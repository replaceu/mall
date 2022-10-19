package com.gulimall.pay.service;

import com.gulimall.pay.dto.CreatePayReqDto;
import com.gulimall.pay.dto.PayRefundDto;
import com.gulimall.pay.dto.PayStatusDto;
import com.gulimall.pay.dto.WeixinPayResDto;

public interface WxPayService {
	WeixinPayResDto weixinNativePay(CreatePayReqDto createPayReq);

	void recordPaymentInfo(String plainText);

	void userCancelOrderPay(String orderId);

	PayStatusDto queryOrderPayStatus(String orderId);

	void refundOrderPay(PayRefundDto payRefundDto);
}
