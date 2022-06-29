package com.gulimall.pay.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gulimall.common.utils.R;
import com.gulimall.pay.entity.AliPayInfoEntity;

@FeignClient
public interface PaymentFeignService {
	@RequestMapping("/save")
	R save(@RequestBody AliPayInfoEntity paymentInfo);
}
