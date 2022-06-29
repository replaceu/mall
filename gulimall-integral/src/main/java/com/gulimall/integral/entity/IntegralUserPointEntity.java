package com.gulimall.integral.entity;

import java.util.Date;

public class IntegralUserPointEntity {
	private String id;

	private String userId;

	private String integralPoint;

	private Date createDate;

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

	public String getIntegralPoint() {
		return integralPoint;
	}

	public void setIntegralPoint(String integralPoint) {
		this.integralPoint = integralPoint == null ? null : integralPoint.trim();
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
