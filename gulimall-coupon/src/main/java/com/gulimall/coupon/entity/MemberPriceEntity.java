package com.gulimall.coupon.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 商品会员价格
 * 
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-09 09:23:34
 */
@Data
@TableName("sms_member_price")
public class MemberPriceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long		id;
	/**
	 * sku_id
	 */
	private Long		skuId;
	/**
	 * 会员等级id
	 */
	private Long		memberLevelId;
	/**
	 * 会员等级名
	 */
	private String		memberLevelName;
	/**
	 * 会员对应价格
	 */
	private BigDecimal	memberPrice;
	/**
	 * 可否叠加其他优惠[0-不可叠加优惠，1-可叠加]
	 */
	private Integer		addOther;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public Long getMemberLevelId() {
		return memberLevelId;
	}

	public void setMemberLevelId(Long memberLevelId) {
		this.memberLevelId = memberLevelId;
	}

	public String getMemberLevelName() {
		return memberLevelName;
	}

	public void setMemberLevelName(String memberLevelName) {
		this.memberLevelName = memberLevelName == null ? null : memberLevelName.trim();
	}

	public BigDecimal getMemberPrice() {
		return memberPrice;
	}

	public void setMemberPrice(BigDecimal memberPrice) {
		this.memberPrice = memberPrice;
	}

	public Integer getAddOther() {
		return addOther;
	}

	public void setAddOther(Integer addOther) {
		this.addOther = addOther;
	}
}
