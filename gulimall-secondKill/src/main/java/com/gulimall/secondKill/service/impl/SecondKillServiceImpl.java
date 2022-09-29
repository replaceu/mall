package com.gulimall.secondKill.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.gulimall.common.constant.ICommonConstants;
import com.gulimall.common.exception.BizException;
import com.gulimall.common.to.mq.SecondKillOrderTo;
import com.gulimall.common.utils.R;
import com.gulimall.common.vo.MemberRespVo;
import com.gulimall.secondKill.feign.CouponFeignService;
import com.gulimall.secondKill.feign.ProductFeignService;
import com.gulimall.secondKill.interceptor.LoginUserInterceptor;
import com.gulimall.secondKill.service.SecondKillService;
import com.gulimall.secondKill.to.SecondKillSkuRedisTo;
import com.gulimall.secondKill.vo.SecondKillSessionWithSkus;
import com.gulimall.secondKill.vo.SkuInfoVo;

@Service
public class SecondKillServiceImpl implements SecondKillService {
	@Autowired
	StringRedisTemplate	redisTemplate;
	@Autowired
	CouponFeignService	couponFeignService;
	@Autowired
	ProductFeignService	productFeignService;
	@Autowired
	RedissonClient		redissonClient;
	@Autowired
	RabbitTemplate		rabbitTemplate;

	private final String SESSIONS_CACHE_PREFIX = "secondKill:sessions:";

	private final String SKUKILL_CACHE_PREFIX = "secondKill:skus:";

	private final String SKU_STOCK_SEMAPHORE = "secondKill:stock:";

	@Override
	public void uploadSecondKillSkuLatest3Days() {
		//1.扫描最近三天需要参与秒杀的活动
		R lasts3DaySession = couponFeignService.getLasts3DaySession();
		if (lasts3DaySession.getCode() == 0) {
			List<SecondKillSessionWithSkus> data = lasts3DaySession.getData(new TypeReference<List<SecondKillSessionWithSkus>>() {
			});
			//2.缓存活动信息到Redis
			saveSessionInfosToRedis(data);
			//3.缓存获得的关联商品到Redis
			saveSessionSkuInfosToRedis(data);
		}
	}

	/**
	 * 用户获得从Redis缓存中保存的秒杀商品信息
	 * @return
	 */
	@Override
	public List<SecondKillSkuRedisTo> getSecondKillSkuList() {

		Set<String> keys = redisTemplate.keys(SESSIONS_CACHE_PREFIX + "*");
		long currentTime = System.currentTimeMillis();

		for (String key : keys) {
			String replace = key.replace(SESSIONS_CACHE_PREFIX, "");
			String[] split = replace.split("_");

			long startTime = Long.parseLong(split[0]);
			long endTime = Long.parseLong(split[1]);

			//当前活动处于有效期内
			if (currentTime > startTime && currentTime < endTime) {
				//获取这个秒杀场次的所有商品信息
				List<String> range = redisTemplate.opsForList().range(key, -100, 100);
				BoundHashOperations<String, String, String> hashOperations = redisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);
				//这个地方用到了断言
				assert range != null;
				List<String> strings = hashOperations.multiGet(range);
				if (!CollectionUtils.isEmpty(strings)) { return strings.stream().map(e -> JSON.parseObject(e, SecondKillSkuRedisTo.class)).collect(Collectors.toList()); }
			}
			break;
		}
		return null;
	}

	@Override
	public SecondKillSkuRedisTo getSkuSecondKillInfo(Long skuId) {
		//1.找到所有需要参与秒杀商品的key
		BoundHashOperations<String, String, String> hashOperations = redisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);
		Set<String> keys = hashOperations.keys();
		if (null != keys) {
			String regx = "\\d_" + skuId;
			for (String key : keys) {
				if (Pattern.matches(regx, key)) {
					String json = hashOperations.get(key);
					SecondKillSkuRedisTo skuRedisTo = JSON.parseObject(json, SecondKillSkuRedisTo.class);
					//随机码
					long current = new Date().getTime();
					if (current >= skuRedisTo.getStartTime().getTime() && current <= skuRedisTo.getEndTime().getTime()) {
					} else {
						skuRedisTo.setRandomCode(null);
					}
					return skuRedisTo;
				}
			}
		}
		return null;
	}

	/**
	 * 用户进行秒杀，从而创建秒杀订单
	 * @param killId
	 * @param key
	 * @param num
	 * @return
	 */
	public String mallUserDoSecondKill(String killId, String key, Integer num) {
		//从拦截器获取用户信息
		MemberRespVo currentUser = LoginUserInterceptor.loginUser.get();
		//todo:获取当前商品的详细信息
		BoundHashOperations<String, String, String> hashOperations = redisTemplate.boundHashOps("SKUKILL_CACHE_PREFIX");
		String json = hashOperations.get(killId);
		if (!StringUtils.isEmpty(json)) {
			SecondKillSkuRedisTo skuRedisTo = JSON.parseObject(json, SecondKillSkuRedisTo.class);
			//todo:校验合法性
			Long startTime = skuRedisTo.getStartTime().getTime();
			long endTime = skuRedisTo.getEndTime().getTime();
			long currentTime = new Date().getTime();
			long ttl = endTime - startTime;
			//todo:校验时间的合法性
			if (currentTime >= startTime && currentTime <= endTime) {
				//todo:校验随机码和商品id
				String skuRandomCode = skuRedisTo.getRandomCode();
				String skuId = skuRedisTo.getPromotionSessionId() + "_" + skuRedisTo.getSkuInfo().getSkuId();
				if (skuRandomCode.equals(key) && killId.equals(skuId)) {
					//todo:检验数量是否合理
					if (num <= skuRedisTo.getRelationEntities().get(0).getSecondKillLimit()) {
						//todo:验证这个是否买过
						String userRedisKey = currentUser.getId() + "_" + skuId;
						Boolean isLock = redisTemplate.opsForValue().setIfAbsent(userRedisKey, num.toString(), ttl, TimeUnit.MILLISECONDS);
						if (isLock) {
							//todo:如果占位成功，说明该用户没有秒杀过该商品，则继续尝试获取库存信号量
							RSemaphore skuStockSemaphore = redissonClient.getSemaphore(SKU_STOCK_SEMAPHORE);
							boolean acquire = skuStockSemaphore.tryAcquire(num);
							if (acquire) {
								//todo:秒杀成功，快速下单发送MQ消息
								String orderSnTimeId = IdWorker.getTimeId();
								SecondKillOrderTo orderTo = new SecondKillOrderTo();
								orderTo.setMemberId(currentUser.getId());
								orderTo.setOrderSn(orderSnTimeId);
								orderTo.setSkuId(skuRedisTo.getSkuInfo().getSkuId());
								orderTo.setPromotionSessionId(skuRedisTo.getPromotionSessionId());
								orderTo.setNum(num);
								orderTo.setSecondKillPrice(skuRedisTo.getRelationEntities().get(0).getSecondKillPrice());
								rabbitTemplate.convertAndSend("order-event-exchange", "order.secondKill.order", orderTo);
								return orderSnTimeId;
							} else {
								throw new BizException(ICommonConstants.RspCode.secondKillErr);
							}

						} else {
							throw new BizException(ICommonConstants.RspCode.userUnLockErr);
						}
					} else {
						throw new BizException(ICommonConstants.RspCode.userSecondKillNumErr);
					}
				} else {
					throw new BizException(ICommonConstants.RspCode.secondKillErr);
				}
			} else {
				throw new BizException(ICommonConstants.RspCode.secondKillTimeUnMatchErr);
			}
		} else {
			throw new BizException(ICommonConstants.RspCode.secondKillSkuOntExistErr);
		}
	}

	private void saveSessionSkuInfosToRedis(List<SecondKillSessionWithSkus> sessions) {
		//准备hash操作
		BoundHashOperations<String, Object, Object> hashOperations = redisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);

		sessions.stream().forEach(e -> {
			e.getSecondKillSkuRelationEntities().stream().forEach(secondKillSkuVo -> {
				//缓存商品
				SecondKillSkuRedisTo secondKillSkuRedisTo = new SecondKillSkuRedisTo();
				//1.sku的基本信息
				R skuInfo = productFeignService.getSkuInfo(secondKillSkuVo.getSkuId());
				if (skuInfo != null) {
					SkuInfoVo data = skuInfo.getData(new TypeReference<SkuInfoVo>() {
					});
					secondKillSkuRedisTo.setSkuInfo(data);
				}
				//2.sku的秒杀信息
				BeanUtils.copyProperties(secondKillSkuVo, secondKillSkuRedisTo);
				//3.设置当前商品的秒杀时间信息
				secondKillSkuRedisTo.setStartTime(e.getStartTime());
				secondKillSkuRedisTo.setEndTime(e.getEndTime());
				//4.设置随机码
				String token = UUID.randomUUID().toString().replace("_", "");
				secondKillSkuRedisTo.setRandomCode(token);
				//todo:5.使用库存作为分布式信号量，达到限流的效果
				RSemaphore semaphore = redissonClient.getSemaphore(SKU_STOCK_SEMAPHORE + token);
				semaphore.trySetPermits(secondKillSkuVo.getSecondKillCount());

				String jsonString = JSON.toJSONString(secondKillSkuRedisTo);
				hashOperations.put(secondKillSkuVo.getSkuId(), jsonString);

			});
		});
	}

	/**
	 * 在redis中保存秒杀场次信息
	 * @param sessions
	 */
	private void saveSessionInfosToRedis(List<SecondKillSessionWithSkus> sessions) {
		if (!CollectionUtils.isEmpty(sessions)) {
			sessions.stream().forEach(session -> {
				long startTime = session.getStartTime().getTime();
				long endTime = session.getEndTime().getTime();
				String key = SESSIONS_CACHE_PREFIX + startTime + "_" + endTime;
				Boolean hasKey = redisTemplate.hasKey(key);
				if (!hasKey) {
					List<String> collect = session.getSecondKillSkuRelationEntities().stream().map(e -> {
						return e.getPromotionId().toString() + "_" + e.getSkuId().toString();
					}).collect(Collectors.toList());

					//缓存活动信息
					redisTemplate.opsForList().leftPushAll(key, collect);
				}

			});
		}
	}
}
