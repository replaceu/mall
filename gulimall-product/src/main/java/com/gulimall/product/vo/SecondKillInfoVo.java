package com.gulimall.product.vo;

import java.math.BigDecimal;

public class SecondKillInfoVo {
	private Long		id;
	/**
	 * 活动id
	 */
	private Long		promotionId;
	/**
	 * 活动场次id
	 */
	private Long		promotionSessionId;
	/**
	 * 商品id
	 */
	private Long		skuId;
	/**
	 * 秒杀价格
	 */
	private BigDecimal	secondKillPrice;
	/**
	 * 秒杀总量
	 */
	private Integer		secondKillCount;
	/**
	 * 每人限购数量
	 */
	private Integer		secondKillLimit;
	/**
	 * 排序
	 */
	private Integer		secondKillSort;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
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

	public Integer getSecondKillCount() {
		return secondKillCount;
	}

	public void setSecondKillCount(Integer secondKillCount) {
		this.secondKillCount = secondKillCount;
	}

	public Integer getSecondKillLimit() {
		return secondKillLimit;
	}

	public void setSecondKillLimit(Integer secondKillLimit) {
		this.secondKillLimit = secondKillLimit;
	}

	public Integer getSecondKillSort() {
		return secondKillSort;
	}

	public void setSecondKillSort(Integer secondKillSort) {
		this.secondKillSort = secondKillSort;
	}
}
