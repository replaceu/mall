package com.gulimall.order.to;

import java.math.BigDecimal;
import java.util.List;

import com.gulimall.order.entity.OrderEntity;
import com.gulimall.order.entity.OrderItemEntity;

import lombok.Data;

/**
 * @author Carter
 * @date 2021-09-03 05:39
 * @description:创建订单的返回值
 * @version:
 */
@Data
public class OrderCreateTo {

	private OrderEntity order;

	private List<OrderItemEntity> orderItems;

	private BigDecimal payPrice;//订单计算的应付价格

	private BigDecimal farePrice;//运费

	public OrderEntity getOrder() {
		return order;
	}

	public void setOrder(OrderEntity order) {
		this.order = order;
	}

	public List<OrderItemEntity> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemEntity> orderItems) {
		this.orderItems = orderItems;
	}

	public BigDecimal getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(BigDecimal payPrice) {
		this.payPrice = payPrice;
	}

	public BigDecimal getFarePrice() {
		return farePrice;
	}

	public void setFarePrice(BigDecimal farePrice) {
		this.farePrice = farePrice;
	}
}
