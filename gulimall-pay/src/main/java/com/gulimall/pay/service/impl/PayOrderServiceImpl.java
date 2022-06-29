package com.gulimall.pay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gulimall.pay.entity.AliPayInfoEntity;
import com.gulimall.pay.feign.OrderFeignService;
import com.gulimall.pay.feign.PaymentFeignService;
import com.gulimall.pay.service.PayOrderService;
import com.gulimall.pay.vo.PayAsyncVo;

@Service
public class PayOrderServiceImpl implements PayOrderService {
	@Autowired
	PaymentFeignService	paymentFeignService;
	@Autowired
	OrderFeignService	orderFeignService;

	@Override
	public String handlePayResult(PayAsyncVo payAsyncVo) {
		//1.保存交易流水
		AliPayInfoEntity info = new AliPayInfoEntity();
		info.setAlipayTradeNo(payAsyncVo.getTradeNo());
		info.setPaymentStatus(payAsyncVo.getTradeStatus());
		info.setCallbackTime(payAsyncVo.getNotifyTime());
		paymentFeignService.save(info);

		//2.修改订单状态信息
		if (payAsyncVo.getTradeStatus().equals("TRADE_SUCCESS") || payAsyncVo.getTradeStatus().equals("TRADE_FINISHED")) {
			String outTradeNo = payAsyncVo.getOutTradeNo();
			orderFeignService.updateOrderStatus(outTradeNo, 1);
		}
		return "success";
	}

}
