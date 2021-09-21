package com.gulimall.order.vo;

import java.math.BigDecimal;

/**
 * @author Carter
 * @date 2021-09-03 04:38
 * @description:订单提交的时候的参数
 * @version:
 */
public class OrderSubmitVo {

	private Long addrId;

	private Integer payType;//支付方式

	//无需提交需要购买的商品，去购物车重新查询

	private String orderToken;//订单防重令牌token

	private BigDecimal payPrice;//应付总额(验价)

	//用户相关信息从session取出登录的用户

	public Long getAddrId() {
		return addrId;
	}

	public void setAddrId(Long addrId) {
		this.addrId = addrId;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getOrderToken() {
		return orderToken;
	}

	public void setOrderToken(String orderToken) {
		this.orderToken = orderToken == null ? null : orderToken.trim();
	}

	public BigDecimal getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(BigDecimal payPrice) {
		this.payPrice = payPrice;
	}
}
