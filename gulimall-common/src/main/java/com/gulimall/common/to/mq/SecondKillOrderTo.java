package com.gulimall.common.to.mq;

import java.math.BigDecimal;

public class SecondKillOrderTo {
	/**
	 * 订单号
	 */
	private String orderSn;

	/**
	 * 活动场次id
	 */
	private Long promotionSessionId;

	/**
	 * 商品id
	 */
	private Long skuId;

	/**
	 * 秒杀价格
	 */
	private BigDecimal secondKillPrice;

	/**
	 * 购买数量
	 */
	private Integer num;

	/**
	 * 会员id
	 */
	private Long memberId;

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn == null ? null : orderSn.trim();
	}

	public Long getPromotionSessionId() {
		return promotionSessionId;
	}

	public void setPromotionSessionId(Long promotionSessionId) {
		this.promotionSessionId = promotionSessionId;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public BigDecimal getSecondKillPrice() {
		return secondKillPrice;
	}

	public void setSecondKillPrice(BigDecimal secondKillPrice) {
		this.secondKillPrice = secondKillPrice;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
}
