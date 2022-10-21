package com.gulimall.pay.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.TypeReference;
import com.gulimall.common.utils.R;
import com.gulimall.pay.dto.PayRefundDto;
import com.gulimall.pay.dto.PayRefundInfoDto;
import com.gulimall.pay.feign.OrderFeignService;
import com.gulimall.pay.service.RefundPayService;
import com.gulimall.pay.vo.PayVo;

@Service
public class RefundPayServiceImpl implements RefundPayService {
	@Autowired
	OrderFeignService orderFeignService;

	@Override
	public PayRefundInfoDto createRefundByOrderId(PayRefundDto payRefundDto) {
		R originalOrderR = orderFeignService.getOrderPay(payRefundDto.getOrderId());
		PayVo originalOrder = originalOrderR.getData(new TypeReference<PayVo>() {
		});

		PayRefundInfoDto insertRefund = new PayRefundInfoDto();
		insertRefund.setOrderId(payRefundDto.getOrderId());
		insertRefund.setReason(payRefundDto.getRefundReason());
		insertRefund.setRefundFee(originalOrder.getTotalAmount());
		insertRefund.setRefundId(UUID.randomUUID().toString());
		return null;
	}
}
