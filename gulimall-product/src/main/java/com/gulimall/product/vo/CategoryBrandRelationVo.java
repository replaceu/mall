package com.gulimall.product.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.gulimall.common.valid.AddGroup;

import lombok.Data;

/**
 * 品牌 与 分类 关联关系数据
 * @author aqiang9  2020-07-30
 */
@Data
public class CategoryBrandRelationVo {
    private Long id;
    /**
     * 品牌id
     */
    @NotNull(message = "品牌id缺失" , groups = AddGroup.class)
    @Positive(message = "品牌id必须大于0" ,groups = AddGroup.class)
    private Long brandId;
    /**
     * 分类id
     */
    @NotNull(message = "分类id缺失", groups = AddGroup.class)
    @Positive(message = "分类id必须大于0", groups = AddGroup.class)
    private Long categoryId;
    /**
     *  品牌名
     */
    private String brandName;
    /**
     * 分类名
     */
    private String categoryName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }
}
