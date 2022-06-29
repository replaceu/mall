package com.gulimall.integral.entity;

import java.util.Date;

public class IntegralFeeGradeEntity {
	private String id;

	private String locName;

	private String greaterAmount;

	private String showName;

	private String lastDay;

	private String sort;

	private Date createDate;

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort == null ? null : sort.trim();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getLocName() {
		return locName;
	}

	public void setLocName(String locName) {
		this.locName = locName == null ? null : locName.trim();
	}

	public String getGreaterAmount() {
		return greaterAmount;
	}

	public void setGreaterAmount(String greaterAmount) {
		this.greaterAmount = greaterAmount == null ? null : greaterAmount.trim();
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName == null ? null : showName.trim();
	}

	public String getLastDay() {
		return lastDay;
	}

	public void setLastDay(String lastDay) {
		this.lastDay = lastDay == null ? null : lastDay.trim();
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
