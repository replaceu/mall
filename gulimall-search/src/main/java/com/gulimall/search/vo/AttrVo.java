package com.gulimall.search.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author aqiang9  2020-08-22
 */
@Getter
@Setter
public class AttrVo {
    private Long  attrId ;
    private String attrName;
    private List<String> attrValue;

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

    public List<String> getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(List<String> attrValue) {
        this.attrValue = attrValue;
    }
}
