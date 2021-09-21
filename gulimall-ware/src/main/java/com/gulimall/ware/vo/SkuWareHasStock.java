package com.gulimall.ware.vo;

import java.util.List;

import lombok.Data;

/**
 * @author Carter
 * @date 2021-09-09 04:58
 * @description:
 * @version:
 */

@Data
public class SkuWareHasStock {
	private Long		skuId;
	private List<Long>	wareId;
	private Integer		num;

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public List<Long> getWareId() {
		return wareId;
	}

	public void setWareId(List<Long> wareId) {
		this.wareId = wareId;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
}
