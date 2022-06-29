package com.gulimall.product.vo;

import java.util.List;

import com.gulimall.product.entity.SkuImagesEntity;
import com.gulimall.product.entity.SkuInfoEntity;
import com.gulimall.product.entity.SpuInfoDescEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
*@author aqiang9 2020-08-24
*/
@Getter
@Setter
@ToString
public class SkuItemVo {
	//1、sku详细信息sku_info
	private SkuInfoEntity	info;
	private boolean			hasStock	= true;
	//2、sku图片信息sku_img
	private List<SkuImagesEntity> images;
	//3、spu销售属性组合
	private List<SkuItemSaleAttrVo> saleAttr;
	//4、spu的介绍
	private SpuInfoDescEntity desc;
	//5、规格参数信息
	private List<SpuItemAttrGroupVo> groupAttrs;
	//6.秒杀活动信息
	private SecondKillInfoVo secondKillInfo;

	public SecondKillInfoVo getSecondKillInfo() {
		return secondKillInfo;
	}

	public void setSecondKillInfo(SecondKillInfoVo secondKillInfo) {
		this.secondKillInfo = secondKillInfo;
	}

	public SkuInfoEntity getInfo() {
		return info;
	}

	public void setInfo(SkuInfoEntity info) {
		this.info = info;
	}

	public boolean isHasStock() {
		return hasStock;
	}

	public void setHasStock(boolean hasStock) {
		this.hasStock = hasStock;
	}

	public List<SkuImagesEntity> getImages() {
		return images;
	}

	public void setImages(List<SkuImagesEntity> images) {
		this.images = images;
	}

	public List<SkuItemSaleAttrVo> getSaleAttr() {
		return saleAttr;
	}

	public void setSaleAttr(List<SkuItemSaleAttrVo> saleAttr) {
		this.saleAttr = saleAttr;
	}

	public SpuInfoDescEntity getDesc() {
		return desc;
	}

	public void setDesc(SpuInfoDescEntity desc) {
		this.desc = desc;
	}

	public List<SpuItemAttrGroupVo> getGroupAttrs() {
		return groupAttrs;
	}

	public void setGroupAttrs(List<SpuItemAttrGroupVo> groupAttrs) {
		this.groupAttrs = groupAttrs;
	}
}
