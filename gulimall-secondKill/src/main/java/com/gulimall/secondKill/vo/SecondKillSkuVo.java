package com.gulimall.secondKill.vo;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SecondKillSkuVo {

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

}
