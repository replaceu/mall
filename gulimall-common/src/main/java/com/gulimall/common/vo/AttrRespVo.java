package com.gulimall.common.vo;

import lombok.Data;

import java.util.List;

/**
 * @author aqiang9  2020-07-30
 */

@Data
public class AttrRespVo extends AttrVo {
    private String attrGroupName ;
    private String categoryName ;
    /**
     * 分类路径
     */
    private List<Long> categoryPath ;

    public String getAttrGroupName() {
        return attrGroupName;
    }

    public void setAttrGroupName(String attrGroupName) {
        this.attrGroupName = attrGroupName == null ? null : attrGroupName.trim();
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public List<Long> getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(List<Long> categoryPath) {
        this.categoryPath = categoryPath;
    }
}
