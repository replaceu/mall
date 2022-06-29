package com.gulimall.pay.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gulimall.common.utils.R;

@FeignClient
public interface OrderFeignService {
	@RequestMapping("/orderPay")
	R getOrderPay(String orderSn);

	@PostMapping("/pay.html")
	void updateOrderStatus(String outTradeNo, Integer code);

}
