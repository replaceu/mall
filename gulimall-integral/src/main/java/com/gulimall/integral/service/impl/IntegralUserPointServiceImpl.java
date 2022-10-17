package com.gulimall.integral.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gulimall.common.utils.CommonResult;
import com.gulimall.integral.dao.IntegralUserFreeDao;
import com.gulimall.integral.dao.IntegralUserPointDao;
import com.gulimall.integral.entity.IntegralUserPointEntity;
import com.gulimall.integral.feign.OrderFeignService;
import com.gulimall.integral.service.IntegralUserPointService;
import com.gulimall.integral.to.OrderTo;

@Service
public class IntegralUserPointServiceImpl implements IntegralUserPointService {
	@Autowired
	IntegralUserPointDao	integralUserPointDao;
	@Autowired
	OrderFeignService		orderFeignService;
	@Autowired
	IntegralUserFreeDao		integralUserFreeDao;

	/**
	 * 更改用户的积分
	 * @param orderTo
	 */
	@Override
	public void updateUserPoint(OrderTo orderTo) {
		CommonResult<OrderTo> orderInfoTo = orderFeignService.getOrderStatus(orderTo.getOrderSn());
		if (orderInfoTo.isOk()) {
			OrderTo orderInfo = orderInfoTo.getData();
			BigDecimal payAmount = orderInfo.getPayAmount();
			IntegralUserPointEntity userPoint = integralUserPointDao.selectOne(new QueryWrapper<IntegralUserPointEntity>().eq("userId", orderInfo.getMemberId()));
			if (userPoint != null) {
				String toAddPoint = orderInfo.getPayAmount().toString();

			}
		}
	}
}
