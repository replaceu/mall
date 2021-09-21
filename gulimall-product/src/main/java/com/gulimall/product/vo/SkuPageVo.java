package com.gulimall.product.vo;

import java.math.BigDecimal;

import com.gulimall.common.vo.PageVo;

import lombok.Data;

/**
 * @author aqiang9  2020-08-02
 */
@Data
public class SkuPageVo extends PageVo {
    private Long categoryId  ;
    private Long brandId ;
   private BigDecimal min  ;
   private BigDecimal max ;

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

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }
}
