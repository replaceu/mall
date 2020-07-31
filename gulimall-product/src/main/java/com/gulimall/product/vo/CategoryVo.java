package com.gulimall.product.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gulimall.common.valid.AddGroup;
import com.gulimall.common.valid.ListValue;
import com.gulimall.common.valid.UpdateGroup;
import com.gulimall.common.valid.UpdateStatusGroup;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.List;

/**
 * 分类 的 页面数据
 * @author aqiang9  2020-07-27
 */
@Data
public class CategoryVo   {
    /**
     * 分类id
     */
    @Null(message = "菜单id不能指定",groups = AddGroup.class)
    @NotNull(message = "菜单id不能缺失",groups = {UpdateGroup.class , UpdateStatusGroup.class})
    private Long catId;
    /**
     * 分类名称
     */
    @NotEmpty(message = "菜单名不能缺失",groups = {AddGroup.class})
    private String name;
    /**
     * 父分类id
     */
    private Long parentCid;
    /**
     * 层级
     */
    private Integer catLevel;
    /**
     * 是否显示[0-不显示，1显示]
     */
    @NotNull(message = "转态值缺失",groups = {AddGroup.class , UpdateStatusGroup.class})
    @ListValue(value = {0,1} , groups = {AddGroup.class , UpdateStatusGroup.class})
    private Integer showStatus;
    /**
     * 排序
     */
    @PositiveOrZero(message = "排序字段必须大于等于0",groups = {AddGroup.class , UpdateGroup.class})
    private Integer sort;
    /**
     * 图标地址
     */
    private String icon;
    /**
     * 计量单位
     */
    @NotBlank(message = "请指定计价单位",groups = {AddGroup.class})
    private String productUnit;
    /**
     * 商品数量
     */
    @PositiveOrZero(message = "商品数量必须 大于0 ")
    private Integer productCount;

    /**
     * 子菜单
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CategoryVo> children ;
}