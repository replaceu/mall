package com.gulimall.integral.entity;

import java.util.Date;

public class IntegralUserFreeEntity {
    private String id;

    private String userId;

    private String memberFreeGradeId;

    private String gradeAmount;

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

    public String getMemberFreeGradeId() {
        return memberFreeGradeId;
    }

    public void setMemberFreeGradeId(String memberFreeGradeId) {
        this.memberFreeGradeId = memberFreeGradeId == null ? null : memberFreeGradeId.trim();
    }

    public String getGradeAmount() {
        return gradeAmount;
    }

    public void setGradeAmount(String gradeAmount) {
        this.gradeAmount = gradeAmount == null ? null : gradeAmount.trim();
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
