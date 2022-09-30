package com.gulimall.ware.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 商品库存
 */
@Data
@TableName("wms_ware_sku")
public class WareSkuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long	id;
	/**
	 * sku_id
	 */
	private Long	skuId;
	/**
	 * 仓库id
	 */
	private Long	wareId;
	/**
	 * 库存数
	 */
	private Integer	stock;
	/**
	 * sku_name
	 */
	private String	skuName;
	/**
	 * 锁定库存
	 */
	private Integer	stockLocked;

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

	public Long getWareId() {
		return wareId;
	}

	public void setWareId(Long wareId) {
		this.wareId = wareId;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName == null ? null : skuName.trim();
	}

	public Integer getStockLocked() {
		return stockLocked;
	}

	public void setStockLocked(Integer stockLocked) {
		this.stockLocked = stockLocked;
	}
}
