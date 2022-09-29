package com.gulimall.ware.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.nacos.common.util.UuidUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.common.constant.ICommonConstants;
import com.gulimall.common.exception.BizException;
import com.gulimall.common.to.SkuHasStockTo;
import com.gulimall.common.to.SkuInfoTo;
import com.gulimall.common.to.mq.StockLockedTo;
import com.gulimall.common.utils.CommonResult;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.service.utils.QueryPage;
import com.gulimall.ware.dao.WareSkuDao;
import com.gulimall.ware.entity.WareOrderTaskDetailEntity;
import com.gulimall.ware.entity.WareOrderTaskEntity;
import com.gulimall.ware.entity.WareSkuEntity;
import com.gulimall.ware.feign.OrderFeignService;
import com.gulimall.ware.feign.ProductFeignService;
import com.gulimall.ware.service.WareOrderTaskDetailService;
import com.gulimall.ware.service.WareOrderTaskService;
import com.gulimall.ware.service.WareSkuService;
import com.gulimall.ware.to.OrderTo;
import com.gulimall.ware.vo.OrderItemVo;
import com.gulimall.ware.vo.SkuWareHasStock;
import com.gulimall.ware.vo.WareSkuLockVo;
import com.gulimall.ware.vo.WareSkuPageVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

	@Autowired
	ProductFeignService			productFeignService;
	@Autowired
	WareSkuDao					wareSkuDao;
	@Autowired
	RabbitTemplate				rabbitTemplate;
	@Autowired
	WareOrderTaskService		orderTaskService;
	@Autowired
	WareOrderTaskDetailService	orderTaskDetailService;
	@Autowired
	OrderFeignService			orderFeignService;

	@Override
	public PageUtils queryPage(WareSkuPageVo params) {
		LambdaQueryWrapper<WareSkuEntity> wrapper = new LambdaQueryWrapper<>();

		if (params.getWareId() != null && params.getWareId() > 0) {
			wrapper.eq(WareSkuEntity::getWareId, params.getWareId());
		}
		if (params.getSkuId() != null && params.getSkuId() > 0) {
			wrapper.eq(WareSkuEntity::getSkuId, params.getSkuId());
		}

		IPage<WareSkuEntity> page = this.page(new QueryPage<WareSkuEntity>().getPage(params), wrapper);
		return new PageUtils(page);
	}

	@Override
	public void addStock(Long skuId, Long wareId, Integer skuNum) {
		//1、判断如果还没有这个库存记录新增
		List<WareSkuEntity> entities = baseMapper.selectList(new LambdaQueryWrapper<WareSkuEntity>().eq(WareSkuEntity::getSkuId, skuId).eq(WareSkuEntity::getWareId, wareId));
		if (entities == null || entities.size() == 0) {
			WareSkuEntity skuEntity = new WareSkuEntity();
			skuEntity.setSkuId(skuId);
			skuEntity.setStock(skuNum);
			skuEntity.setWareId(wareId);
			skuEntity.setStockLocked(0);
			//TODO 远程查询sku的名字，如果失败，整个事务无需回滚
			//1、自己catch异常
			//TODO 还可以用什么办法让异常出现以后不回滚？高级
			try {
				CommonResult<SkuInfoTo> resp = productFeignService.info(skuId);
				if (resp.getCode() == 0 && resp.getData() != null) {
					SkuInfoTo data = resp.getData();
					skuEntity.setSkuName(data.getSkuName());
				}
			} catch (Exception e) {
				//log.error("远程调用异常，{}", e.getMessage());
			}
			baseMapper.insert(skuEntity);
		} else {
			baseMapper.addStock(skuId, wareId, skuNum);
		}
	}

	@Override
	public List<SkuHasStockTo> getSkuHasStock(List<Long> skuIds) {
		//逐一检查
		List<SkuHasStockTo> collect = skuIds.stream().map(skuId -> {
			SkuHasStockTo skuHasStockTo = new SkuHasStockTo();
			Long count = baseMapper.selectStockCount(skuId);
			skuHasStockTo.setSkuId(skuId);
			skuHasStockTo.setHasStock(count != null && count > 0);
			return skuHasStockTo;
		}).collect(Collectors.toList());

		return collect;
	}

	/**
	 * 用户提交订单后锁定库存
	 * 库存解锁的场景：
	 * 1.下订单成功，订单过期没有支付被系统自动取消或者用户手动取消
	 * 2.下订单成功，库存锁定成功但是接下来的业务调用失败，导致订单回滚，之前锁定的库存就需要自动解锁
	 * 
	 * @param wareSkuLock
	 * @return
	 */
	public Boolean orderLockStock(WareSkuLockVo wareSkuLock) {
		//todo:保存库存工作单
		WareOrderTaskEntity wareOrderTaskEntity = new WareOrderTaskEntity();
		wareOrderTaskEntity.setOrderSn(wareSkuLock.getOrderSn());
		wareOrderTaskEntity.setCreateTime(new Date());
		wareOrderTaskEntity.setId(Long.parseLong(UuidUtils.generateUuid()));
		orderTaskService.save(wareOrderTaskEntity);
		//todo:找到商品在哪个仓库都有库存，然后来锁定库存
		List<OrderItemVo> orderLockList = wareSkuLock.getLocks();
		List<SkuWareHasStock> skuWareHasStockList = orderLockList.stream().map(e -> {
			SkuWareHasStock skuWareHasStock = new SkuWareHasStock();
			skuWareHasStock.setSkuId(e.getSkuId());
			skuWareHasStock.setNum(e.getCount());
			List<Long> wareIds = wareSkuDao.listWareIdHasStock(e.getSkuId());
			skuWareHasStock.setWareId(wareIds);
			return skuWareHasStock;
		}).collect(Collectors.toList());
		//todo:锁定订单当中所有涉及到的skuId库存
		Boolean allSkuLocked = true;
		for (SkuWareHasStock skuWareHasStock : skuWareHasStockList) {
			Boolean skuLocked = false;
			Long skuId = skuWareHasStock.getSkuId();
			List<Long> wareIds = skuWareHasStock.getWareId();
			if (wareIds == null || wareIds.size() == 0) { throw new BizException(ICommonConstants.RspCode.wareHasNoSkuErr); }
			//todo:对于所有的仓库进行遍历，看看哪一个仓库可以锁住
			for (Long wareId : wareIds) {
				Long effectCount = wareSkuDao.lockSkuStock(skuId, wareId, skuWareHasStock.getNum());
				if (effectCount == 1) {
					//todo:当前仓库锁成功了，也就没有必要锁下一个了，发消息给MQ
					skuLocked = true;
					WareOrderTaskDetailEntity wareOrderTaskDetailEntity = new WareOrderTaskDetailEntity();
					wareOrderTaskDetailEntity.setWareId(wareId);
					wareOrderTaskDetailEntity.setTaskId(wareOrderTaskEntity.getId());
					wareOrderTaskDetailEntity.setSkuId(skuId);
					wareOrderTaskDetailEntity.setSkuNum(skuWareHasStock.getNum());
					wareOrderTaskDetailEntity.setLockStatus(1);
					orderTaskDetailService.save(wareOrderTaskDetailEntity);
					StockLockedTo stockLockedTo = new StockLockedTo();
					stockLockedTo.setDetailId(wareOrderTaskDetailEntity.getId());
					stockLockedTo.setId(wareOrderTaskEntity.getId());
					BeanUtils.copyProperties(wareOrderTaskDetailEntity, stockLockedTo);
					rabbitTemplate.convertAndSend("stock-event-exchange", "stock.locked", stockLockedTo);
					break;
				}
			}
			if (skuLocked == false) {
				//todo:表示当前商品在所有仓库都没有锁住
				throw new BizException(ICommonConstants.RspCode.wareHasNoSkuErr);
			}
		}
		return true;
	}

	/**
	 * 解锁库存
	 * 如果库存工作单不为空，说明库存锁定成功
	 * @param stockLockedTo
	 */
	@Transactional
	@Override
	public void unLock(StockLockedTo stockLockedTo) {
		WareOrderTaskDetailEntity orderTaskDetail = orderTaskDetailService.getById(stockLockedTo.getDetailId());
		//1.如果工作单详情不为空，说明库存锁定成功
		if (orderTaskDetail != null) {
			WareOrderTaskEntity task = orderTaskService.getById(stockLockedTo.getId());
			//2.查询订单的状态码
			CommonResult orderStatus = orderFeignService.getOrderStatus(task.getOrderSn());
			if (orderStatus.getCode() == 0) {
				OrderTo orderTo = new OrderTo();
				BeanUtils.copyProperties(orderStatus, orderTo);
				//3.没有这个订单或者订单已经取消就需要解锁库存
				if (orderTo == null || orderTo.getStatus() == 0) {
					//为保证幂等性，只有当工作单详情处于被锁定的情况下才进行解锁
					if (orderTaskDetail.getLockStatus() == 1) {
						wareSkuDao.unLockStock(orderTaskDetail.getSkuId(), orderTaskDetail.getWareId(), orderTaskDetail.getSkuNum(), orderTaskDetail.getId());
					}
				}

			} else {
				throw new RuntimeException("远程调用订单服务失败");
			}

		}
	}

	@Transactional
	@Override
	public void unLockStockForOrder(OrderTo orderTo) {
		//查询最新的库存解锁状态，防止重复解锁库存
		CommonResult orderStatus = orderFeignService.getOrderStatus(orderTo.getOrderSn());
		if (orderStatus.getCode() != 0) { throw new BizException(ICommonConstants.RspCode.skuUnLockErr); }
		WareOrderTaskEntity taskEntity = orderTaskService.getOrderTaskByOrderSn(orderTo.getOrderSn());
		//按照工作单找到所有没有解锁的库存，进行解锁
		List<WareOrderTaskDetailEntity> taskDetailList = orderTaskDetailService.list(new QueryWrapper<WareOrderTaskDetailEntity>().eq("task_id", taskEntity.getId()).eq("lock_status", 1));
		for (WareOrderTaskDetailEntity detailEntity : taskDetailList) {
			wareSkuDao.unLockStock(detailEntity.getSkuId(), detailEntity.getWareId(), detailEntity.getSkuNum(), detailEntity.getId());
		}

	}

}