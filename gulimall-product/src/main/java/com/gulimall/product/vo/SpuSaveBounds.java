package com.gulimall.product.vo;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SpuSaveBounds {

    private BigDecimal buyBounds;
    private BigDecimal growBounds;

    public BigDecimal getBuyBounds() {
        return buyBounds;
    }

    public void setBuyBounds(BigDecimal buyBounds) {
        this.buyBounds = buyBounds;
    }

    public BigDecimal getGrowBounds() {
        return growBounds;
    }

    public void setGrowBounds(BigDecimal growBounds) {
        this.growBounds = growBounds;
    }
}