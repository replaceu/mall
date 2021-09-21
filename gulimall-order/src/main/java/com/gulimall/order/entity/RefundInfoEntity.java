package com.gulimall.order.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 退款信息
 * 
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-09 10:01:26
 */
@Data
@TableName("oms_refund_info")
public class RefundInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long		id;
	/**
	 * 退款的订单
	 */
	private Long		orderReturnId;
	/**
	 * 退款金额
	 */
	private BigDecimal	refund;
	/**
	 * 退款交易流水号
	 */
	private String		refundSn;
	/**
	 * 退款状态
	 */
	private Integer		refundStatus;
	/**
	 * 退款渠道[1-支付宝，2-微信，3-银联，4-汇款]
	 */
	private Integer		refundChannel;
	/**
	 * 
	 */
	private String		refundContent;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderReturnId() {
		return orderReturnId;
	}

	public void setOrderReturnId(Long orderReturnId) {
		this.orderReturnId = orderReturnId;
	}

	public BigDecimal getRefund() {
		return refund;
	}

	public void setRefund(BigDecimal refund) {
		this.refund = refund;
	}

	public String getRefundSn() {
		return refundSn;
	}

	public void setRefundSn(String refundSn) {
		this.refundSn = refundSn == null ? null : refundSn.trim();
	}

	public Integer getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}

	public Integer getRefundChannel() {
		return refundChannel;
	}

	public void setRefundChannel(Integer refundChannel) {
		this.refundChannel = refundChannel;
	}

	public String getRefundContent() {
		return refundContent;
	}

	public void setRefundContent(String refundContent) {
		this.refundContent = refundContent == null ? null : refundContent.trim();
	}
}
