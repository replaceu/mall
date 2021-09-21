package com.gulimall.product.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 属性分组
 * 
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-10 11:26:28
 */
@Data
@TableName("pms_attr_group")
public class AttrGroupEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 分组id
	 */
	@TableId
	private Long	attrGroupId;
	/**
	 * 组名
	 */
	private String	attrGroupName;
	/**
	 * 排序
	 */
	private Integer	sort;
	/**
	 * 描述
	 */
	private String	descript;
	/**
	 * 组图标
	 */
	private String	icon;
	/**
	 * 所属分类id
	 */
	private Long	categoryId;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getAttrGroupId() {
		return attrGroupId;
	}

	public void setAttrGroupId(Long attrGroupId) {
		this.attrGroupId = attrGroupId;
	}

	public String getAttrGroupName() {
		return attrGroupName;
	}

	public void setAttrGroupName(String attrGroupName) {
		this.attrGroupName = attrGroupName == null ? null : attrGroupName.trim();
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript == null ? null : descript.trim();
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon == null ? null : icon.trim();
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
}
