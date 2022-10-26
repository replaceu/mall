package com.gulimall.order.constant;

/**
 * @author Carter
 * @date 2021-09-03 04:28
 * @description:
 * @version:
 */
public interface OrderConstant {
	public static final String USER_ORDER_TOKEN_PREFIX = "order:token:";

	interface OrderStatus {
		int	notPay	= 2;
		int	paid	= 3;
		int	refund	= 4;
	}

	interface OrderRedisKey {
		String orderPayToken = "orderPayToken:";
	}
}
