package com.gulimall.pay.vo;

import java.util.Date;

public class PayAsyncVo {
	private String	gmtCreate;
	private String	charset;
	private String	gmtPayment;
	private Date	notifyTime;
	private String	subject;
	private String	sign;
	private String	buyerId;		//支付者的id
	private String	body;			//订单的信息
	private String	invoiceAmount;	//支付金额
	private String	version;
	private String	notifyId;		//通知id
	private String	fundBillList;
	private String	notifyType;		//通知类型； trade_status_sync
	private String	outTradeNo;		//订单号
	private String	totalAmount;	//支付的总额
	private String	tradeStatus;	//交易状态  TRADE_SUCCESS
	private String	tradeNo;		//流水号
	private String	authAppId;		//
	private String	receiptAmount;	//商家收到的款
	private String	pointAmount;	//
	private String	appId;			//应用id
	private String	buyerPayAmount;	//最终支付的金额
	private String	signType;		//签名类型
	private String	sellerId;		//商家的id

	public String getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(String gmtCreate) {
		this.gmtCreate = gmtCreate == null ? null : gmtCreate.trim();
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset == null ? null : charset.trim();
	}

	public String getGmtPayment() {
		return gmtPayment;
	}

	public void setGmtPayment(String gmtPayment) {
		this.gmtPayment = gmtPayment == null ? null : gmtPayment.trim();
	}

	public Date getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(Date notifyTime) {
		this.notifyTime = notifyTime;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject == null ? null : subject.trim();
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign == null ? null : sign.trim();
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId == null ? null : buyerId.trim();
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body == null ? null : body.trim();
	}

	public String getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(String invoiceAmount) {
		this.invoiceAmount = invoiceAmount == null ? null : invoiceAmount.trim();
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version == null ? null : version.trim();
	}

	public String getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId == null ? null : notifyId.trim();
	}

	public String getFundBillList() {
		return fundBillList;
	}

	public void setFundBillList(String fundBillList) {
		this.fundBillList = fundBillList == null ? null : fundBillList.trim();
	}

	public String getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType == null ? null : notifyType.trim();
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo == null ? null : outTradeNo.trim();
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount == null ? null : totalAmount.trim();
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus == null ? null : tradeStatus.trim();
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo == null ? null : tradeNo.trim();
	}

	public String getAuthAppId() {
		return authAppId;
	}

	public void setAuthAppId(String authAppId) {
		this.authAppId = authAppId == null ? null : authAppId.trim();
	}

	public String getReceiptAmount() {
		return receiptAmount;
	}

	public void setReceiptAmount(String receiptAmount) {
		this.receiptAmount = receiptAmount == null ? null : receiptAmount.trim();
	}

	public String getPointAmount() {
		return pointAmount;
	}

	public void setPointAmount(String pointAmount) {
		this.pointAmount = pointAmount == null ? null : pointAmount.trim();
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId == null ? null : appId.trim();
	}

	public String getBuyerPayAmount() {
		return buyerPayAmount;
	}

	public void setBuyerPayAmount(String buyerPayAmount) {
		this.buyerPayAmount = buyerPayAmount == null ? null : buyerPayAmount.trim();
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType == null ? null : signType.trim();
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId == null ? null : sellerId.trim();
	}
}
