package com.gulimall.ware.feign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gulimall.common.utils.CommonResult;

public interface OrderFeignService {
	@GetMapping("/status/{orderSn}")
	CommonResult getOrderStatus(@PathVariable("orderSn") String orderSn);
}
