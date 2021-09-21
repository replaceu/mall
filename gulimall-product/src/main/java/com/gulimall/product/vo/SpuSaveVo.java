package com.gulimall.product.vo;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SpuSaveVo {
    private String spuName;
    private String spuDescription;
    private Long categoryId;
    private Long brandId;
    private BigDecimal weight;
    private int publishStatus;
    /**
     * 描述信息
     */
    private List<String> decript;
    private List<String> images;
    private SpuSaveBounds bounds;
    private List<SpuSaveBaseAttrs> baseAttrs;
    private List<SpuSaveSku> skus;

    public String getSpuName() {
        return spuName;
    }

    public void setSpuName(String spuName) {
        this.spuName = spuName == null ? null : spuName.trim();
    }

    public String getSpuDescription() {
        return spuDescription;
    }

    public void setSpuDescription(String spuDescription) {
        this.spuDescription = spuDescription == null ? null : spuDescription.trim();
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public int getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(int publishStatus) {
        this.publishStatus = publishStatus;
    }

    public List<String> getDecript() {
        return decript;
    }

    public void setDecript(List<String> decript) {
        this.decript = decript;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public SpuSaveBounds getBounds() {
        return bounds;
    }

    public void setBounds(SpuSaveBounds bounds) {
        this.bounds = bounds;
    }

    public List<SpuSaveBaseAttrs> getBaseAttrs() {
        return baseAttrs;
    }

    public void setBaseAttrs(List<SpuSaveBaseAttrs> baseAttrs) {
        this.baseAttrs = baseAttrs;
    }

    public List<SpuSaveSku> getSkus() {
        return skus;
    }

    public void setSkus(List<SpuSaveSku> skus) {
        this.skus = skus;
    }
}