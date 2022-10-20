package com.gulimall.pay.entity;

public class PayRefundInfoDo {

	private String orderId;

	private String refundPayId;

	private String refundId;

	private String totalFee;

	private String refundFee;

	private String reason;

	private String refundStatus;

	private String contentReturn;//申请退款返回参数

	private String contentNotify;//退款结果通知参数

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId == null ? null : orderId.trim();
	}

	public String getRefundPayId() {
		return refundPayId;
	}

	public void setRefundPayId(String refundPayId) {
		this.refundPayId = refundPayId == null ? null : refundPayId.trim();
	}

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId == null ? null : refundId.trim();
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee == null ? null : totalFee.trim();
	}

	public String getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(String refundFee) {
		this.refundFee = refundFee == null ? null : refundFee.trim();
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason == null ? null : reason.trim();
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus == null ? null : refundStatus.trim();
	}

	public String getContentReturn() {
		return contentReturn;
	}

	public void setContentReturn(String contentReturn) {
		this.contentReturn = contentReturn == null ? null : contentReturn.trim();
	}

	public String getContentNotify() {
		return contentNotify;
	}

	public void setContentNotify(String contentNotify) {
		this.contentNotify = contentNotify == null ? null : contentNotify.trim();
	}
}
