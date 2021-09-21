package com.gulimall.order.vo;

import com.gulimall.order.entity.OrderEntity;

/**
 * @author Carter
 * @date 2021-09-03 04:58
 * @description:订单提交返回的数据
 * @version:
 */
public class OrderSubmitResponseVo {

	private OrderEntity order;

	private Integer code;//错误状态码

	public OrderEntity getOrder() {
		return order;
	}

	public void setOrder(OrderEntity order) {
		this.order = order;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
}
