package com.gulimall.order.vo;

import lombok.Data;

/**
 * @author Carter
 * @date 2021-08-31 02:36
 * @description:
 * @version:
 */

@Data
public class SkuStockVo {
	private Long	skuId;
	private Boolean	hasStock;

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public Boolean getHasStock() {
		return hasStock;
	}

	public void setHasStock(Boolean hasStock) {
		this.hasStock = hasStock;
	}
}
