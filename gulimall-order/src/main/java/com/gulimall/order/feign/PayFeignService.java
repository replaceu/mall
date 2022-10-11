package com.gulimall.order.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.annotations.ApiOperation;

@FeignClient("gulimall-pay")
public interface PayFeignService {
	@GetMapping("/pay/decrypt")
	@ApiOperation("解密微信支付报文")
	String weixinDecrypt(Map<String, Object> bodyMap);

	@GetMapping("/pay/recordPaymentInfo")
	@ApiOperation("记录支付日志")
	void recordPaymentInfo(String plainText);

}
