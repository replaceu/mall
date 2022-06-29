package com.gulimall.member.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.gulimall.common.utils.R;

@FeignClient
public interface OrderFeignService {
	@PostMapping("order/order/listWithItem")
	public R listWithItems(@RequestBody Map<String, Object> params);
}
