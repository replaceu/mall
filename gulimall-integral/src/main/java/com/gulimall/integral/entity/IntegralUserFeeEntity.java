package com.gulimall.integral.entity;

import java.util.Date;

public class IntegralUserFeeEntity {
	private String id;

	private String userId;

	private String memberFeeGradeId;

	private String lastRefData;

	private Date createDate;

	private Date updateDate;

	private Date effectDate;

	private Date expiredDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId == null ? null : userId.trim();
	}

	public String getMemberFeeGradeId() {
		return memberFeeGradeId;
	}

	public void setMemberFeeGradeId(String memberFeeGradeId) {
		this.memberFeeGradeId = memberFeeGradeId == null ? null : memberFeeGradeId.trim();
	}

	public String getLastRefData() {
		return lastRefData;
	}

	public void setLastRefData(String lastRefData) {
		this.lastRefData = lastRefData == null ? null : lastRefData.trim();
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}
}
