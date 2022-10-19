package com.gulimall.pay.dto;

public class PayRefundDto {
	private String orderId;

	private String refundReason;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId == null ? null : orderId.trim();
	}

	public String getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason == null ? null : refundReason.trim();
	}
}
