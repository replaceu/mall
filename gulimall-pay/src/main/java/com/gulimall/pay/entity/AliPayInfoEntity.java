package com.gulimall.pay.entity;

import java.util.Date;

public class AliPayInfoEntity {
	String alipayTradeNo;

	String paymentStatus;

	Date callbackTime;

	public String getAlipayTradeNo() {
		return alipayTradeNo;
	}

	public void setAlipayTradeNo(String alipayTradeNo) {
		this.alipayTradeNo = alipayTradeNo == null ? null : alipayTradeNo.trim();
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus == null ? null : paymentStatus.trim();
	}

	public Date getCallbackTime() {
		return callbackTime;
	}

	public void setCallbackTime(Date callbackTime) {
		this.callbackTime = callbackTime;
	}
}
