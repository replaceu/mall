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
public class SpuItemAttrGroupVo {
    private String groupName;
    private List<SpuItemAttrVo> attrs;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public List<SpuItemAttrVo> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<SpuItemAttrVo> attrs) {
        this.attrs = attrs;
    }
}