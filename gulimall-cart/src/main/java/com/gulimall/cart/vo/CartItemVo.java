package com.gulimall.cart.vo;

import java.math.BigDecimal;
import java.util.List;

import lombok.Setter;

/**
 * 购物项
 * @author aqiang9  2020-09-08
 */
@Setter
public class CartItemVo {
	private Long			skuId;
	private Boolean			check	= true;
	private String			title;
	private String			image;
	private List<String>	skuAttr;
	private BigDecimal		price;
	private BigDecimal		totalPrice;
	private Integer			count;

	public Long getSkuId() {
		return skuId;
	}

	public Boolean getCheck() {
		return check;
	}

	public String getTitle() {
		return title;
	}

	public String getImage() {
		return image;
	}

	public List<String> getSkuAttr() {
		return skuAttr;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getTotalPrice() {
		return price.multiply(BigDecimal.valueOf(count));
	}

	public Integer getCount() {
		return count;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public void setCheck(Boolean check) {
		this.check = check;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public void setImage(String image) {
		this.image = image == null ? null : image.trim();
	}

	public void setSkuAttr(List<String> skuAttr) {
		this.skuAttr = skuAttr;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
