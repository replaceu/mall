package com.gulimall.pay.vo;

public class PayVo {
	private String	outTradeNo;		// 商户订单号 必填
	private String	subject;		// 订单名称 必填
	private String	totalAmount;	// 付款金额 必填
	private String	body;			// 商品描述 可空

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo == null ? null : outTradeNo.trim();
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject == null ? null : subject.trim();
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount == null ? null : totalAmount.trim();
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body == null ? null : body.trim();
	}
}
