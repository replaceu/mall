package com.gulimall.secondKill.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gulimall.common.utils.R;

@FeignClient
public interface ProductFeignService {
	/**
	 * 信息
	 */
	@GetMapping("/info/{skuId}")
	//   @RequiresPermissions("product:skuinfo:info")
	R getSkuInfo(@PathVariable("skuId") Long skuId);
}
