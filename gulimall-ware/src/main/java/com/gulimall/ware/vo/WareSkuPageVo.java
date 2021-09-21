package com.gulimall.ware.vo;

import com.gulimall.common.vo.PageVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author aqiang9  2020-08-08
 */
@Getter
@Setter
@ToString
public class WareSkuPageVo extends PageVo {
    private Long wareId;//仓库id
    private Long skuId; // 商品id

    public Long getWareId() {
        return wareId;
    }

    public void setWareId(Long wareId) {
        this.wareId = wareId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }
}
