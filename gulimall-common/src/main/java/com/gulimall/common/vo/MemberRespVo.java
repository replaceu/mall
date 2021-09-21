package com.gulimall.common.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * @author Carter
 * @date 2021-08-29 03:57
 * @description:
 * @version:
 */

@Data
@ToString
public class MemberRespVo implements Serializable {

	private Long	id;
	/**
	 * 会员等级id
	 */
	private Long	levelId;
	/**
	 * 用户名
	 */
	private String	username;
	/**
	 * 密码
	 */
	private String	password;
	/**
	 * 昵称
	 */
	private String	nickname;
	/**
	 * 手机号码
	 */
	private String	mobile;
	/**
	 * 邮箱
	 */
	private String	email;
	/**
	 * 头像
	 */
	private String	header;
	/**
	 * 性别
	 */
	private Integer	gender;
	/**
	 * 生日
	 */
	private Date	birth;
	/**
	 * 所在城市
	 */
	private String	city;
	/**
	 * 职业
	 */
	private String	job;
	/**
	 * 个性签名
	 */
	private String	sign;
	/**
	 * 用户来源
	 */
	private Integer	sourceType;
	/**
	 * 积分
	 */
	private Integer	integration;
	/**
	 * 成长值
	 */
	private Integer	growth;
	/**
	 * 启用状态
	 */
	private Integer	status;
	/**
	 * 注册时间
	 */
	private Date	createTime;

	private String socialUid;

	private String accessToken;

	private long expiresIn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLevelId() {
		return levelId;
	}

	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname == null ? null : nickname.trim();
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile == null ? null : mobile.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header == null ? null : header.trim();
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job == null ? null : job.trim();
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign == null ? null : sign.trim();
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public Integer getIntegration() {
		return integration;
	}

	public void setIntegration(Integer integration) {
		this.integration = integration;
	}

	public Integer getGrowth() {
		return growth;
	}

	public void setGrowth(Integer growth) {
		this.growth = growth;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSocialUid() {
		return socialUid;
	}

	public void setSocialUid(String socialUid) {
		this.socialUid = socialUid == null ? null : socialUid.trim();
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken == null ? null : accessToken.trim();
	}

	public long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}
}
