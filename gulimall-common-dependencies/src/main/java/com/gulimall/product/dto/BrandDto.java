package com.gulimall.product.dto;

import com.gulimall.common.valid.AddGroup;
import com.gulimall.common.valid.ListValue;
import com.gulimall.common.valid.UpdateGroup;
import com.gulimall.common.valid.UpdateStatusGroup;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

/**
 * @author aqiang9  2020-07-28
 */
@Data
public class BrandDto implements Serializable {

    private static final long serialVersionUID = 826584587834L;

    /**
     * 品牌id
     */
    @Null(message = "不能指定品牌id", groups = {AddGroup.class})
    @NotNull(message = "品牌id不能为空",groups = {UpdateGroup.class, UpdateStatusGroup.class})
    private Long brandId;
    /**
     * 品牌名
     */
    @NotEmpty(message = "品牌名不能为空",groups = {AddGroup.class})
    private String name;
    /**
     * 品牌logo地址
     */
    @URL(message = "logo必须是一个合法的地址",groups = {AddGroup.class , UpdateGroup.class})
    @NotNull(message = "logo图片缺失" ,groups = {AddGroup.class})
    private String logo;
    /**
     * 介绍
     */
    private String descript;
    /**
     * 显示状态[0-不显示；1-显示]
     */
    @NotNull(message = "请指定显示状态", groups = {UpdateStatusGroup.class})
    @ListValue(vals = {0,1})
    private Integer showStatus;
    /**
     * 检索首字母
     */
    @NotEmpty(message = "请指定显示状态")
    private String firstLetter;
    /**
     * 排序
     */
    private Integer sort;
}
