package com.gulimall.coupon.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 商品阶梯价格
 * 
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-09 09:23:34
 */
@Data
@TableName("sms_sku_ladder")
public class SkuLadderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long		id;
	/**
	 * spu_id
	 */
	private Long		skuId;
	/**
	 * 满几件
	 */
	private Integer		fullCount;
	/**
	 * 打几折
	 */
	private BigDecimal	discount;
	/**
	 * 折后价
	 */
	private BigDecimal	price;
	/**
	 * 是否叠加其他优惠[0-不可叠加，1-可叠加]
	 */
	private Integer		addOther;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public Integer getFullCount() {
		return fullCount;
	}

	public void setFullCount(Integer fullCount) {
		this.fullCount = fullCount;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getAddOther() {
		return addOther;
	}

	public void setAddOther(Integer addOther) {
		this.addOther = addOther;
	}
}
