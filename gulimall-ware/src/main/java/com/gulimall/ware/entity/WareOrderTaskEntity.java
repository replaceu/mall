package com.gulimall.ware.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 库存工作单
 */
@Data
@TableName("wms_ware_order_task")
public class WareOrderTaskEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long	id;
	/**
	 * order_id
	 */
	private Long	orderId;
	/**
	 * order_sn
	 */
	private String	orderSn;
	/**
	 * 收货人
	 */
	private String	consignee;
	/**
	 * 收货人电话
	 */
	private String	consigneeTel;
	/**
	 * 配送地址
	 */
	private String	deliveryAddress;
	/**
	 * 订单备注
	 */
	private String	orderComment;
	/**
	 * 付款方式【 1:在线付款 2:货到付款】
	 */
	private Integer	paymentWay;
	/**
	 * 任务状态
	 */
	private Integer	taskStatus;
	/**
	 * 订单描述
	 */
	private String	orderBody;
	/**
	 * 物流单号
	 */
	private String	trackingNo;
	/**
	 * create_time
	 */
	private Date	createTime;
	/**
	 * 仓库id
	 */
	private Long	wareId;
	/**
	 * 工作单备注
	 */
	private String	taskComment;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn == null ? null : orderSn.trim();
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee == null ? null : consignee.trim();
	}

	public String getConsigneeTel() {
		return consigneeTel;
	}

	public void setConsigneeTel(String consigneeTel) {
		this.consigneeTel = consigneeTel == null ? null : consigneeTel.trim();
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress == null ? null : deliveryAddress.trim();
	}

	public String getOrderComment() {
		return orderComment;
	}

	public void setOrderComment(String orderComment) {
		this.orderComment = orderComment == null ? null : orderComment.trim();
	}

	public Integer getPaymentWay() {
		return paymentWay;
	}

	public void setPaymentWay(Integer paymentWay) {
		this.paymentWay = paymentWay;
	}

	public Integer getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(Integer taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getOrderBody() {
		return orderBody;
	}

	public void setOrderBody(String orderBody) {
		this.orderBody = orderBody == null ? null : orderBody.trim();
	}

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo == null ? null : trackingNo.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getWareId() {
		return wareId;
	}

	public void setWareId(Long wareId) {
		this.wareId = wareId;
	}

	public String getTaskComment() {
		return taskComment;
	}

	public void setTaskComment(String taskComment) {
		this.taskComment = taskComment == null ? null : taskComment.trim();
	}
}
