package com.gulimall.order.vo;

import com.baomidou.mybatisplus.annotation.TableId;

/**
 * 用户收货地址信息
 * @author aqiang9  2020-09-24
 */
public class MemberAddressVo {
	/**
	 * id
	 */
	@TableId
	private Long	id;
	/**
	 * member_id
	 */
	private Long	memberId;
	/**
	 * 收货人姓名
	 */
	private String	name;
	/**
	 * 电话
	 */
	private String	phone;
	/**
	 * 邮政编码
	 */
	private String	postCode;
	/**
	 * 省份/直辖市
	 */
	private String	province;
	/**
	 * 城市
	 */
	private String	city;
	/**
	 * 区
	 */
	private String	region;
	/**
	 * 详细地址(街道)
	 */
	private String	detailAddress;
	/**
	 * 省市区代码
	 */
	private String	areacode;
	/**
	 * 是否默认
	 */
	private Integer	defaultStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode == null ? null : postCode.trim();
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province == null ? null : province.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region == null ? null : region.trim();
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress == null ? null : detailAddress.trim();
	}

	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode == null ? null : areacode.trim();
	}

	public Integer getDefaultStatus() {
		return defaultStatus;
	}

	public void setDefaultStatus(Integer defaultStatus) {
		this.defaultStatus = defaultStatus;
	}
}
