package com.gulimall.search.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author aqiang9  2020-08-20
 */
@Getter
@Setter
@ToString
public class BrandVo {
    private Long brandId ;
    private String brandName ;
    private String brandImg ;

    public BrandVo() {
    }

    public BrandVo(Long brandId, String brandName, String brandImg) {
        this.brandId = brandId;
        this.brandName = brandName;
        this.brandImg = brandImg;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }

    public String getBrandImg() {
        return brandImg;
    }

    public void setBrandImg(String brandImg) {
        this.brandImg = brandImg == null ? null : brandImg.trim();
    }
}
