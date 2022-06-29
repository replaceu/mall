package com.gulimall.pay.vo;

public class MallOrderTransOsSyncVo {
	String	orderId;
	String	transChannel;
	String	thirdTradeResId;
	String	thirdTradeReqId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTransChannel() {
		return transChannel;
	}

	public void setTransChannel(String transChannel) {
		this.transChannel = transChannel;
	}

	public String getThirdTradeResId() {
		return thirdTradeResId;
	}

	public void setThirdTradeResId(String thirdTradeResId) {
		this.thirdTradeResId = thirdTradeResId;
	}

	public String getThirdTradeReqId() {
		return thirdTradeReqId;
	}

	public void setThirdTradeReqId(String thirdTradeReqId) {
		this.thirdTradeReqId = thirdTradeReqId;
	}
}
