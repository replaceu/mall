package com.gulimall.ware.vo;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

/**
 * @author Carter
 * @date 2021-08-29 04:16
 * @description:
 * @version:
 */
@Data
public class OrderItemVo {
	private Long			skuId;
	private String			title;
	private String			image;
	private List<String>	skuAttr;
	private BigDecimal		price;
	private BigDecimal		totalPrice;
	private Integer			count;
	private BigDecimal		weight;

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image == null ? null : image.trim();
	}

	public List<String> getSkuAttr() {
		return skuAttr;
	}

	public void setSkuAttr(List<String> skuAttr) {
		this.skuAttr = skuAttr;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
}
