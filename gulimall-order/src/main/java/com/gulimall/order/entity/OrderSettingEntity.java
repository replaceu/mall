package com.gulimall.order.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 订单配置信息
 * 
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-09 10:01:26
 */
@Data
@TableName("oms_order_setting")
public class OrderSettingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long	id;
	/**
	 * 秒杀订单超时关闭时间(分)
	 */
	private Integer	flashOrderOvertime;
	/**
	 * 正常订单超时时间(分)
	 */
	private Integer	normalOrderOvertime;
	/**
	 * 发货后自动确认收货时间（天）
	 */
	private Integer	confirmOvertime;
	/**
	 * 自动完成交易时间，不能申请退货（天）
	 */
	private Integer	finishOvertime;
	/**
	 * 订单完成后自动好评时间（天）
	 */
	private Integer	commentOvertime;
	/**
	 * 会员等级【0-不限会员等级，全部通用；其他-对应的其他会员等级】
	 */
	private Integer	memberLevel;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getFlashOrderOvertime() {
		return flashOrderOvertime;
	}

	public void setFlashOrderOvertime(Integer flashOrderOvertime) {
		this.flashOrderOvertime = flashOrderOvertime;
	}

	public Integer getNormalOrderOvertime() {
		return normalOrderOvertime;
	}

	public void setNormalOrderOvertime(Integer normalOrderOvertime) {
		this.normalOrderOvertime = normalOrderOvertime;
	}

	public Integer getConfirmOvertime() {
		return confirmOvertime;
	}

	public void setConfirmOvertime(Integer confirmOvertime) {
		this.confirmOvertime = confirmOvertime;
	}

	public Integer getFinishOvertime() {
		return finishOvertime;
	}

	public void setFinishOvertime(Integer finishOvertime) {
		this.finishOvertime = finishOvertime;
	}

	public Integer getCommentOvertime() {
		return commentOvertime;
	}

	public void setCommentOvertime(Integer commentOvertime) {
		this.commentOvertime = commentOvertime;
	}

	public Integer getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(Integer memberLevel) {
		this.memberLevel = memberLevel;
	}
}
