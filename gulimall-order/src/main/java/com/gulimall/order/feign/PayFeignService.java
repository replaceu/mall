package com.gulimall.order.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.annotations.ApiOperation;

@FeignClient("gulimall-pay")
public interface PayFeignService {
	@GetMapping("/pay/decrypt")
	@ApiOperation("解密报文")
	String weixinDecrypt(Map<String, Object> bodyMap);
}
