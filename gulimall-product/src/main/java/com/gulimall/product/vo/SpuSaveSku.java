package com.gulimall.product.vo;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class SpuSaveSku{

    private List<SpuSaveAttr> attr;
    private String skuName;
    private BigDecimal price;
    private String skuTitle;
    private String skuSubtitle;
    private List<SpuSaveImage> images;
    private List<String> descar;
    private int fullCount;
    private BigDecimal discount;
    private int countStatus;
    private BigDecimal fullPrice;
    private BigDecimal reducePrice;
    private int priceStatus;
    private List<SpuSaveMemberPrice> memberPrice;

    public List<SpuSaveAttr> getAttr() {
        return attr;
    }

    public void setAttr(List<SpuSaveAttr> attr) {
        this.attr = attr;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName == null ? null : skuName.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSkuTitle() {
        return skuTitle;
    }

    public void setSkuTitle(String skuTitle) {
        this.skuTitle = skuTitle == null ? null : skuTitle.trim();
    }

    public String getSkuSubtitle() {
        return skuSubtitle;
    }

    public void setSkuSubtitle(String skuSubtitle) {
        this.skuSubtitle = skuSubtitle == null ? null : skuSubtitle.trim();
    }

    public List<SpuSaveImage> getImages() {
        return images;
    }

    public void setImages(List<SpuSaveImage> images) {
        this.images = images;
    }

    public List<String> getDescar() {
        return descar;
    }

    public void setDescar(List<String> descar) {
        this.descar = descar;
    }

    public int getFullCount() {
        return fullCount;
    }

    public void setFullCount(int fullCount) {
        this.fullCount = fullCount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public int getCountStatus() {
        return countStatus;
    }

    public void setCountStatus(int countStatus) {
        this.countStatus = countStatus;
    }

    public BigDecimal getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(BigDecimal fullPrice) {
        this.fullPrice = fullPrice;
    }

    public BigDecimal getReducePrice() {
        return reducePrice;
    }

    public void setReducePrice(BigDecimal reducePrice) {
        this.reducePrice = reducePrice;
    }

    public int getPriceStatus() {
        return priceStatus;
    }

    public void setPriceStatus(int priceStatus) {
        this.priceStatus = priceStatus;
    }

    public List<SpuSaveMemberPrice> getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(List<SpuSaveMemberPrice> memberPrice) {
        this.memberPrice = memberPrice;
    }
}