package com.gulimall.product.vo;

import lombok.Data;

/**
 * @author aqiang9  2020-08-01
 */
@Data
public class SpuSaveBaseAttrs {
    private Long attrId;
    private String attrValues;
    private int showDesc;

    public Long getAttrId() {
        return attrId;
    }

    public void setAttrId(Long attrId) {
        this.attrId = attrId;
    }

    public String getAttrValues() {
        return attrValues;
    }

    public void setAttrValues(String attrValues) {
        this.attrValues = attrValues == null ? null : attrValues.trim();
    }

    public int getShowDesc() {
        return showDesc;
    }

    public void setShowDesc(int showDesc) {
        this.showDesc = showDesc;
    }
}
