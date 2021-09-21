package com.gulimall.product.vo;

import java.math.BigDecimal;

import lombok.Data;

/**
 * @author aqiang9  2020-08-01
 */
@Data
public class SpuSaveBrands {
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
