package com.gulimall.integral.service.impl;

import com.gulimall.integral.service.IntegralUserPointService;
import com.gulimall.integral.to.OrderTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IntegralUserPointServiceImpl implements IntegralUserPointService {
    @Autowired

    /**
     * 更改用户的积分
     * @param orderTo
     */
    @Override
    public void updateUserPoint(OrderTo orderTo) {

    }
}
