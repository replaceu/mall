package com.gulimall.product.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author aqiang9  2020-08-25
 */
@Getter
@Setter
@ToString
public class SkuItemSaleAttrVo {
    private Long attrId;
    private String attrName;
//    private String attrValues;
    private List<AttrValueWithSkuIdVo> attrValues ;

    public Long getAttrId() {
        return attrId;
    }

    public void setAttrId(Long attrId) {
        this.attrId = attrId;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName == null ? null : attrName.trim();
    }

    public List<AttrValueWithSkuIdVo> getAttrValues() {
        return attrValues;
    }

    public void setAttrValues(List<AttrValueWithSkuIdVo> attrValues) {
        this.attrValues = attrValues;
    }
}