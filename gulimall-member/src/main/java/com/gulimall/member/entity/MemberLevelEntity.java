package com.gulimall.member.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 会员等级
 * 
 * @author aqiang9
 * @email ${email}
 * @date 2020-07-27 10:19:25
 */
@Data
@TableName("ums_member_level")
public class MemberLevelEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long		id;
	/**
	 * 等级名称
	 */
	private String		name;
	/**
	 * 等级需要的成长值
	 */
	private Integer		growthPoint;
	/**
	 * 是否为默认等级[0->不是；1->是]
	 */
	private Integer		defaultStatus;
	/**
	 * 免运费标准
	 */
	private BigDecimal	freeFreightPoint;
	/**
	 * 每次评价获取的成长值
	 */
	private Integer		commentGrowthPoint;
	/**
	 * 是否有免邮特权
	 */
	private Integer		priviledgeFreeFreight;
	/**
	 * 是否有会员价格特权
	 */
	private Integer		priviledgeMemberPrice;
	/**
	 * 是否有生日特权
	 */
	private Integer		priviledgeBirthday;
	/**
	 * 备注
	 */
	private String		note;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Integer getGrowthPoint() {
		return growthPoint;
	}

	public void setGrowthPoint(Integer growthPoint) {
		this.growthPoint = growthPoint;
	}

	public Integer getDefaultStatus() {
		return defaultStatus;
	}

	public void setDefaultStatus(Integer defaultStatus) {
		this.defaultStatus = defaultStatus;
	}

	public BigDecimal getFreeFreightPoint() {
		return freeFreightPoint;
	}

	public void setFreeFreightPoint(BigDecimal freeFreightPoint) {
		this.freeFreightPoint = freeFreightPoint;
	}

	public Integer getCommentGrowthPoint() {
		return commentGrowthPoint;
	}

	public void setCommentGrowthPoint(Integer commentGrowthPoint) {
		this.commentGrowthPoint = commentGrowthPoint;
	}

	public Integer getPriviledgeFreeFreight() {
		return priviledgeFreeFreight;
	}

	public void setPriviledgeFreeFreight(Integer priviledgeFreeFreight) {
		this.priviledgeFreeFreight = priviledgeFreeFreight;
	}

	public Integer getPriviledgeMemberPrice() {
		return priviledgeMemberPrice;
	}

	public void setPriviledgeMemberPrice(Integer priviledgeMemberPrice) {
		this.priviledgeMemberPrice = priviledgeMemberPrice;
	}

	public Integer getPriviledgeBirthday() {
		return priviledgeBirthday;
	}

	public void setPriviledgeBirthday(Integer priviledgeBirthday) {
		this.priviledgeBirthday = priviledgeBirthday;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note == null ? null : note.trim();
	}
}
