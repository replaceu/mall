package com.gulimall.pay.service;

import com.gulimall.pay.vo.PayAsyncVo;

public interface PayOrderService {

	String handlePayResult(PayAsyncVo payAsyncVo);
}
