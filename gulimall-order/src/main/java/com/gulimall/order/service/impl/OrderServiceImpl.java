package com.gulimall.order.service.impl;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.gulimall.common.to.SkuHasStockTo;
import com.gulimall.common.to.mq.SecondKillOrderTo;
import com.gulimall.common.utils.CommonResult;
import com.gulimall.common.utils.R;
import com.gulimall.common.vo.MemberRespVo;
import com.gulimall.order.constant.OrderConstant;
import com.gulimall.order.dao.OrderDao;
import com.gulimall.order.entity.OrderEntity;
import com.gulimall.order.entity.OrderItemEntity;
import com.gulimall.order.feign.CartFeignService;
import com.gulimall.order.feign.MemberFeignService;
import com.gulimall.order.feign.PayFeignService;
import com.gulimall.order.feign.WmsFeignService;
import com.gulimall.order.interceptor.LoginUserInterceptor;
import com.gulimall.order.service.OrderItemService;
import com.gulimall.order.service.OrderService;
import com.gulimall.order.to.OrderCreateTo;
import com.gulimall.order.to.OrderPayTo;
import com.gulimall.order.to.OrderTo;
import com.gulimall.order.vo.*;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.service.utils.Query;

@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

	@Autowired
	private MemberFeignService	memberFeignService;
	@Autowired
	private CartFeignService	cartFeignService;
	@Autowired
	private WmsFeignService		wmsFeignService;
	@Autowired
	private OrderItemService	orderItemService;
	@Autowired
	private OrderDao			orderDao;
	@Autowired
	private ThreadPoolExecutor	executor;
	@Autowired
	private StringRedisTemplate	redisTemplate;
	@Autowired
	private RabbitTemplate		rabbitTemplate;
	@Autowired
	private PayFeignService		payFeignService;

	private ThreadLocal<OrderSubmitVo> submitThreadLocal = new ThreadLocal<>();

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<OrderEntity> page = this.page(new Query<OrderEntity>().getPage(params), new QueryWrapper<OrderEntity>());
		return new PageUtils(page);
	}

	//确认订单
	@Override
	public OrderConfirmVo confirmOrder() throws ExecutionException, InterruptedException {
		OrderConfirmVo retVo = new OrderConfirmVo();
		MemberRespVo currentUser = LoginUserInterceptor.loginUser.get();
		RequestAttributes attributes = RequestContextHolder.getRequestAttributes();

		CompletableFuture<Void> addressFuture = CompletableFuture.runAsync(() -> {
			RequestContextHolder.setRequestAttributes(attributes);
			//1.远程查询用户收货地址列表
			List<MemberAddressVo> addressList = memberFeignService.getAddress(currentUser.getId());
			retVo.setAddress(addressList);
		}, executor);

		CompletableFuture<Void> cartItemsFuture = CompletableFuture.runAsync(() -> {
			RequestContextHolder.setRequestAttributes(attributes);
			//2.远程查询购物车所有选中的购物项
			List<OrderItemVo> cartItems = cartFeignService.getCurrentUserCartItems();
			retVo.setItems(cartItems);
		}, executor).thenRunAsync(() -> {
			//todo:批量查询所有商品的库存状态
			List<OrderItemVo> items = retVo.getItems();
			List<Long> skuIdList = items.stream().map(e -> e.getSkuId()).collect(Collectors.toList());
			CommonResult<List<SkuHasStockTo>> hasStock = wmsFeignService.hasStock(skuIdList);
			List<SkuStockVo> data = hasStock.getData().stream().map(e -> {
				SkuStockVo skuStockVo = new SkuStockVo();
				BeanUtils.copyProperties(e, skuStockVo);
				return skuStockVo;
			}).collect(Collectors.toList());
			if (data != null) {
				Map<Long, Boolean> map = data.stream().collect(Collectors.toMap(SkuStockVo::getSkuId, SkuStockVo::getHasStock));
				retVo.setStocks(map);
			}
		}, executor);

		/**
		 * todo:Feign远程调用丢失请求头问题,解决方案是加上拦截器
		 * todo:Feign异步情况丢失上下文问题,解决方案在各个线程里面将请求数据加进去
		 */

		//3.查询积分
		Integer integration = currentUser.getIntegration();
		retVo.setIntegration(integration);

		//todo:防重令牌token,需要放进页面数据
		String token = UUID.randomUUID().toString().replace("-", "");
		retVo.setOrderToken(token);
		redisTemplate.opsForValue().set(OrderConstant.USER_ORDER_TOKEN_PREFIX + currentUser.getId(), token, 20, TimeUnit.MINUTES);

		CompletableFuture.allOf(addressFuture, cartItemsFuture).get();
		return retVo;
	}

	@Override
	public OrderEntity getOrderByOrderSn(String orderSn) {
		OrderDao orderDao = this.getBaseMapper();
		OrderEntity orderEntity = orderDao.selectOne(new QueryWrapper<OrderEntity>().eq("order_sn", orderSn));
		return orderEntity;
	}

	@Override
	public void closeOrder(OrderEntity orderEntity) {
		//1.查询这个订单最新的订单状态
		OrderEntity entity = orderDao.selectById(orderEntity.getId());
		if (entity.getStatus() == 0) {
			//关单
			OrderEntity update = new OrderEntity();
			update.setId(orderEntity.getId());
			update.setStatus(1);//订单取消
			orderDao.updateById(update);
			//将数据传入到MQ
			OrderTo orderTo = new OrderTo();
			BeanUtils.copyProperties(orderEntity, orderTo);
			//给MQ发送消息
			rabbitTemplate.convertAndSend("order-event-exchange", "order.release.other", orderTo);

		}
	}

	@Override
	public R getOrderPayByOrderSn(String orderSn) {
		OrderPayTo retTo = new OrderPayTo();
		OrderDao baseMapper = this.getBaseMapper();
		OrderEntity orderEntity = baseMapper.selectOne(new QueryWrapper<OrderEntity>().eq("order_sn", orderSn));
		if (orderEntity != null) {
			BigDecimal payAmount = orderEntity.getPayAmount().setScale(2, BigDecimal.ROUND_UP);
			retTo.setTotalAmount(payAmount.toString());
			retTo.setOutTradeNo(orderSn);

			List<OrderItemEntity> orderItemEntities = orderItemService.list(new QueryWrapper<OrderItemEntity>().eq("order_sn", orderSn));
			retTo.setSubject(orderItemEntities.get(0).getSkuName());
			retTo.setBody(orderItemEntities.get(0).getSkuAttrsVals());

		}
		return R.ok().setData(retTo);

	}

	@Override
	public PageUtils queryPageWithItems(Map<String, Object> params) {
		MemberRespVo memberRespVo = LoginUserInterceptor.loginUser.get();
		IPage<OrderEntity> orderEntityIPage = this.page(new Query<OrderEntity>().getPage(params), new QueryWrapper<OrderEntity>().eq("member_id", memberRespVo.getId()));

		List<OrderEntity> orderEntityList = orderEntityIPage.getRecords().stream().map(e -> {
			List<OrderItemEntity> orderItemEntities = orderItemService.list(new QueryWrapper<OrderItemEntity>().eq("order_sn", e.getOrderSn()));
			e.setItemEntityList(orderItemEntities);
			return e;
		}).collect(Collectors.toList());
		orderEntityIPage.setRecords(orderEntityList);
		return new PageUtils(orderEntityIPage);
	}

	@Override
	public void updateOrderStatus(String outTradeNo, Integer code) {
		this.baseMapper.updateOrderStatus(outTradeNo, code);
	}

	@Override
	public void creatSecondKillOrder(SecondKillOrderTo secondKillOrder) {
		//todo:保存订单信息
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setOrderSn(secondKillOrder.getOrderSn());
		orderEntity.setMemberId(secondKillOrder.getMemberId());
		orderEntity.setCreateTime(new Date());
		BigDecimal payAmount = secondKillOrder.getSecondKillPrice().multiply(new BigDecimal("" + secondKillOrder.getNum()));
		orderEntity.setPayAmount(payAmount);
		this.save(orderEntity);
		//todo:保存订单项信息
		OrderItemEntity orderItemEntity = new OrderItemEntity();
		orderItemEntity.setOrderSn(secondKillOrder.getOrderSn());
		orderItemEntity.setRealAmount(payAmount);
		orderItemEntity.setSkuQuantity(secondKillOrder.getNum());
		orderItemService.save(orderItemEntity);

	}

	@Override
	@Transactional
	public void processOrder(Map<String, Object> map) {
		//todo:解密报文
		String plainText = payFeignService.weixinDecrypt(map);
		Gson gson = new Gson();
		HashMap plainTextMap = gson.fromJson(plainText, HashMap.class);
		String orderId = (String) plainTextMap.get("out_trade_no");
		/**在对业务数据进行状态检查和处理之前，要采用数据锁进行并发控制，
		以避免函数重入造成的数据混乱*/
		OrderEntity order = getOrderByOrderSn(orderId);
		if (OrderConstant.OrderStatus.notPay != order.getStatus()) { return; }
		//todo:更新订单状态
		updateOrderStatus(orderId,OrderConstant.OrderStatus.paid);
		//todo:记录支付日志
		payFeignService.recordPaymentInfo(plainText);
		//todo:发送相关信息给积分系统
		OrderEntity entity = orderDao.selectById(orderId);
		if (entity!=null){
			OrderTo orderTo = new OrderTo();
			BeanUtils.copyProperties(entity,orderTo);
			rabbitTemplate.convertAndSend("order-event-exchange", "order.finish.integral", orderTo);
		}


	}

	/**
	 * 保存订单数据
	 * @param order
	 */
	private void saveOrder(OrderCreateTo order) {
		OrderEntity orderEntity = order.getOrder();
		orderEntity.setModifyTime(new Date());

		this.save(orderEntity);
		List<OrderItemEntity> orderItems = order.getOrderItems();
		orderItemService.saveBatch(orderItems);

	}

	private OrderCreateTo createOrder() {
		OrderCreateTo retTo = new OrderCreateTo();
		//1.生成订单号
		String orderSn = IdWorker.getTimeId();
		OrderEntity orderEntity = buildOrder(orderSn);

		//2.构建订单项
		List<OrderItemEntity> orderItems = buildOrderItems(orderSn);

		//3.计算价格相关
		computerPrice(orderEntity, orderItems);

		return retTo;

	}

	private void computerPrice(OrderEntity orderEntity, List<OrderItemEntity> orderItems) {
		BigDecimal total = new BigDecimal("0.0");
		BigDecimal integration = new BigDecimal("0.0");
		BigDecimal promotion = new BigDecimal("0.0");
		BigDecimal coupon = new BigDecimal("0.0");
		BigDecimal gift = new BigDecimal("0.0");
		BigDecimal growth = new BigDecimal("0.0");
		//订单的总额，就是叠加每一个订单项的总额信息
		for (OrderItemEntity orderItem : orderItems) {
			BigDecimal orderItemPrice = orderItem.getRealAmount();
			BigDecimal integrationAmount = orderItem.getIntegrationAmount();
			BigDecimal promotionAmount = orderItem.getPromotionAmount();
			BigDecimal couponAmount = orderItem.getCouponAmount();
			gift = gift.add(new BigDecimal(orderItem.getGiftIntegration()));
			growth = growth.add(new BigDecimal(orderItem.getGiftGrowth()));

			total = total.add(orderItemPrice);
			integration = integration.add(integrationAmount);
			coupon = coupon.add(couponAmount);
			promotion = promotion.add(promotionAmount);

		}
		//订单价格相关信息
		orderEntity.setTotalAmount(total);
		orderEntity.setPayAmount(total.add(orderEntity.getFreightAmount()));
		orderEntity.setPromotionAmount(promotion);
		orderEntity.setIntegrationAmount(integration);
		orderEntity.setCouponAmount(coupon);
		orderEntity.setIntegration(gift.intValue());
		orderEntity.setGrowth(growth.intValue());

	}

	private OrderEntity buildOrder(String orderSn) {
		MemberRespVo member = LoginUserInterceptor.loginUser.get();
		OrderEntity entity = new OrderEntity();
		entity.setOrderSn(orderSn);
		entity.setMemberId(member.getId());
		//2.设置运费
		OrderSubmitVo orderSubmit = submitThreadLocal.get();
		R fare = wmsFeignService.getFare(orderSubmit.getAddrId());
		FareVo fareResp = new FareVo();
		BeanUtils.copyProperties(fare.get("fare"), fareResp);
		entity.setReceiverDetailAddress(fareResp.getAddress().getDetailAddress());
		entity.setFreightAmount(fareResp.getFare());
		//3.获取收货人信息
		entity.setReceiverCity(fareResp.getAddress().getCity());
		entity.setReceiverName(fareResp.getAddress().getName());
		entity.setReceiverPhone(fareResp.getAddress().getPhone());
		entity.setReceiverProvince(fareResp.getAddress().getProvince());
		entity.setReceiverRegion(fareResp.getAddress().getRegion());
		entity.setReceiverPostCode(fareResp.getAddress().getPostCode());
		//4.设置订单状态
		entity.setStatus(1);
		entity.setAutoConfirmDay(7);
		return entity;
	}

	/**
	 * 构建订单项列表
	 * @param
	 * @return
	 */
	private List<OrderItemEntity> buildOrderItems(String orderSn) {
		List<OrderItemEntity> orderItems = new ArrayList<>();
		List<OrderItemVo> cartItems = cartFeignService.getCurrentUserCartItems();
		if (cartItems.size() > 0 && cartItems != null) {
			//todo:将购物项数据映射成订单项数据
			List<OrderItemEntity> orderItemList = cartItems.stream().map(e -> {
				OrderItemEntity orderItem = new OrderItemEntity();
				orderItem = buildOrderItem(e);
				orderItem.setOrderSn(orderSn);
				return orderItem;
			}).collect(Collectors.toList());
			return orderItemList;
		}
		return orderItems;
	}

	/**
	 * 构建某个订单项
	 * @param cartItem
	 * @return
	 */
	private OrderItemEntity buildOrderItem(OrderItemVo cartItem) {
		OrderItemEntity orderItem = new OrderItemEntity();
		//1.订单信息：订单号
		//2.商品的spu信息
		Long skuId = cartItem.getSkuId();

		//3.商品的sku信息
		orderItem.setSkuId(cartItem.getSkuId());
		orderItem.setSkuName(cartItem.getTitle());
		orderItem.setSkuPic(cartItem.getImage());
		orderItem.setSkuPrice(cartItem.getPrice());
		orderItem.setSkuQuantity(cartItem.getCount());
		orderItem.setSkuAttrsVals(StringUtils.collectionToCommaDelimitedString(cartItem.getSkuAttr()));
		//4.优惠信息
		//5.积分信息
		orderItem.setGiftIntegration(cartItem.getPrice().intValue());
		orderItem.setGiftGrowth(cartItem.getPrice().intValue());
		//6.订单项的价格信息,实际工作中需要从各个优惠信息的数据库中查出来
		orderItem.setPromotionAmount(new BigDecimal("0"));
		orderItem.setCouponAmount(new BigDecimal("0"));
		orderItem.setIntegrationAmount(new BigDecimal("0"));
		//当前订单项的实际金额
		BigDecimal orgin = orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getSkuQuantity().toString()));
		orgin = orgin.subtract(orderItem.getCouponAmount()).subtract(orderItem.getIntegrationAmount()).subtract(orderItem.getPromotionAmount());

		orderItem.setRealAmount(orgin);

		return orderItem;
	}

	/**
	 * 下单
	 * @param orderSubmitVo
	 * @return
	 */
	@Override
	@Transactional
	public OrderSubmitResponseVo submitOrder(OrderSubmitVo orderSubmitVo) {
		//todo:验证令牌，即页面提交的参数与Redis数据库中存放的是否一致
		MemberRespVo currentUser = LoginUserInterceptor.loginUser.get();
		submitThreadLocal.set(orderSubmitVo);
		//todo:script语句，令牌的验证以及删除必须保证原子性
		String script = "if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
		OrderSubmitResponseVo responseVo = new OrderSubmitResponseVo();
		String orderToken = orderSubmitVo.getOrderToken();
		Long executeResult = redisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class), Arrays.asList(OrderConstant.USER_ORDER_TOKEN_PREFIX + currentUser.getId()), orderToken);
		if (executeResult == 0L) {
			return responseVo;
		} else {
			//todo:验证成功，去创建订单
			responseVo.setCode(1);
			OrderCreateTo order = createOrder();
			//todo:进行验价，需要比较前端参数传入的价格
			BigDecimal payAmount = order.getOrder().getPayAmount();
			BigDecimal payPrice = orderSubmitVo.getPayPrice();
			if (Math.abs(payAmount.subtract(payPrice).doubleValue()) < 0.01) {
				//todo:金额对比成功，将订单进行保存
				saveOrder(order);
				//todo:订单保存之后需要找到订单性当中的sku列表，锁定库存
				WareSkuLockVo skuLockVo = new WareSkuLockVo();
				skuLockVo.setOrderSn(order.getOrder().getOrderSn());
				List<OrderItemVo> orderItems = order.getOrderItems().stream().map(e -> {
					OrderItemVo orderItemVo = new OrderItemVo();
					orderItemVo.setCount(e.getSkuQuantity());
					orderItemVo.setSkuId(e.getSkuId());
					orderItemVo.setTitle(e.getSkuName());
					return orderItemVo;
				}).collect(Collectors.toList());
				skuLockVo.setLocks(orderItems);
				//todo:调用库存服务进行远程锁库存
				CommonResult lockStockResult = wmsFeignService.orderLockStock(skuLockVo);
				if (lockStockResult.getCode() == 0) {
					return responseVo;
				} else {
					responseVo.setCode(3);
				}
			} else {
				responseVo.setCode(2);
				return responseVo;
			}

		}
		return responseVo;
	}

}