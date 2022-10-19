package com.gulimall.pay.dto;

public class PayStatusDto {
	private String orderId;

	private String orderPayStatus;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId == null ? null : orderId.trim();
	}

	public String getOrderPayStatus() {
		return orderPayStatus;
	}

	public void setOrderPayStatus(String orderPayStatus) {
		this.orderPayStatus = orderPayStatus == null ? null : orderPayStatus.trim();
	}
}
