package com.gulimall.integral.service;

import com.gulimall.integral.to.OrderTo;

public interface IntegralUserPointService {
	void updateUserPoint(OrderTo orderTo);

	void reduceUserPoint(OrderTo orderTo);
}
