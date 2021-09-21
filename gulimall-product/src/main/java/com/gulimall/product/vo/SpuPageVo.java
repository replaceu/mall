package com.gulimall.product.vo;

import com.gulimall.common.vo.PageVo;

import lombok.Data;

/**
 * @author aqiang9  2020-08-02
 */
@Data
public class SpuPageVo extends PageVo {
    private Long categoryId  ;
    private Long brandId ;
    private Integer status ;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
