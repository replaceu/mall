package com.gulimall.ware.vo;

import lombok.Data;

/**
 * @author Carter
 * @date 2021-09-09 04:13
 * @description:库存锁定的结果
 * @version:
 */

@Data
public class LockStockResultVo {
	private Long	skuId;
	private Integer	num;
	private Boolean	locked;

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}
}
