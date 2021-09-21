package com.gulimall.order.vo;

import java.util.List;

/**
 * @author Carter
 * @date 2021-09-09 04:03
 * @description:库存锁定的Vo
 * @version:
 */
public class WareSkuLockVo {
	private String orderSn;//订单号

	private List<OrderItemVo> locks;//需要锁住的所有库存信息

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn == null ? null : orderSn.trim();
	}

	public List<OrderItemVo> getLocks() {
		return locks;
	}

	public void setLocks(List<OrderItemVo> locks) {
		this.locks = locks;
	}
}
