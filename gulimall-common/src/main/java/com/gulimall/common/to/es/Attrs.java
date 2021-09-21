package com.gulimall.common.to.es;

import lombok.Getter;
import lombok.Setter;

/**
 * @author aqiang9  2020-08-15
 */
@Getter
@Setter
public  class Attrs {
    private Long attrId;
    private String attrName;
    private String attrValue;

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

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue == null ? null : attrValue.trim();
    }
}
