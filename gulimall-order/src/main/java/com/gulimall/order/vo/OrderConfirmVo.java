package com.gulimall.order.vo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author aqiang9  2020-09-24
 */
@Getter
@Setter
@ToString
public class OrderConfirmVo {
	// 地址信息
	List<MemberAddressVo> address;

	//所有选中的购物项
	List<OrderItemVo> items;

	//发票信息

	//优惠券信息
	Integer integration;

	//订单总额
	BigDecimal totalPrice;

	//应付总额
	BigDecimal payPrice;

	//订单的防重令牌
	String orderToken;

	//skuId对应着是否有库存
	Map<Long, Boolean> stocks;

	public String getOrderToken() {
		return orderToken;
	}

	public void setOrderToken(String orderToken) {
		this.orderToken = orderToken == null ? null : orderToken.trim();
	}

	public List<MemberAddressVo> getAddress() {
		return address;
	}

	public void setAddress(List<MemberAddressVo> address) {
		this.address = address;
	}

	public List<OrderItemVo> getItems() {
		return items;
	}

	public void setItems(List<OrderItemVo> items) {
		this.items = items;
	}

	public Integer getIntegration() {
		return integration;
	}

	public void setIntegration(Integer integration) {
		this.integration = integration;
	}

	public BigDecimal getTotalPrice() {
		BigDecimal sum = new BigDecimal("0");
		if (items != null) {
			for (OrderItemVo item : items) {
				sum = sum.add(item.getPrice().multiply(new BigDecimal(item.getCount().toString())));
			}
		}

		return sum;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public BigDecimal getPayPrice() {
		BigDecimal sum = new BigDecimal("0");
		if (items != null) {
			for (OrderItemVo item : items) {
				sum = sum.add(item.getPrice().multiply(new BigDecimal(item.getCount().toString())));
			}
		}
		return sum;
	}

	public void setPayPrice(BigDecimal payPrice) {
		this.payPrice = payPrice;
	}

	public Map<Long, Boolean> getStocks() {
		return stocks;
	}

	public void setStocks(Map<Long, Boolean> stocks) {
		this.stocks = stocks;
	}
}
