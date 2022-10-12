package com.gulimall.integral.entity;

import java.util.Date;

public class IntegralMemberFreeGradeEntity {
    private String id;

    private String locName;

    private String growthName;

    private String greaterAmount;

    private String showName;

    private String showImg;

    private String lineImg;

    private String userImg;

    private String lockedImg;

    private String unlockImg;

    private String currentImg;

    private String lastDay;

    private String sort;

    private Date createDate;

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

    public String getGrowthName() {
        return growthName;
    }

    public void setGrowthName(String growthName) {
        this.growthName = growthName == null ? null : growthName.trim();
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

    public String getShowImg() {
        return showImg;
    }

    public void setShowImg(String showImg) {
        this.showImg = showImg == null ? null : showImg.trim();
    }

    public String getLineImg() {
        return lineImg;
    }

    public void setLineImg(String lineImg) {
        this.lineImg = lineImg == null ? null : lineImg.trim();
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg == null ? null : userImg.trim();
    }

    public String getLockedImg() {
        return lockedImg;
    }

    public void setLockedImg(String lockedImg) {
        this.lockedImg = lockedImg == null ? null : lockedImg.trim();
    }

    public String getUnlockImg() {
        return unlockImg;
    }

    public void setUnlockImg(String unlockImg) {
        this.unlockImg = unlockImg == null ? null : unlockImg.trim();
    }

    public String getCurrentImg() {
        return currentImg;
    }

    public void setCurrentImg(String currentImg) {
        this.currentImg = currentImg == null ? null : currentImg.trim();
    }

    public String getLastDay() {
        return lastDay;
    }

    public void setLastDay(String lastDay) {
        this.lastDay = lastDay == null ? null : lastDay.trim();
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort == null ? null : sort.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
