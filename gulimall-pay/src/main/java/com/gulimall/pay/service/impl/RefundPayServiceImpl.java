package com.gulimall.pay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gulimall.common.utils.R;
import com.gulimall.pay.dto.PayRefundDto;
import com.gulimall.pay.dto.PayRefundInfoDto;
import com.gulimall.pay.feign.OrderFeignService;
import com.gulimall.pay.service.RefundPayService;

@Service
public class RefundPayServiceImpl implements RefundPayService {
	@Autowired
	OrderFeignService orderFeignService;

	@Override
	public PayRefundInfoDto createRefundByOrderId(PayRefundDto payRefundDto) {
		R originalOrder = orderFeignService.getOrderPay(payRefundDto.getOrderId());

		return null;
	}
}
