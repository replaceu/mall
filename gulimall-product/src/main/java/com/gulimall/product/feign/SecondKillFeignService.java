package com.gulimall.product.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gulimall.common.utils.R;

@FeignClient
public interface SecondKillFeignService {
	/**
	 * 返回当前时间可以参与的秒杀商品信息
	 */

	@GetMapping(value = "/getCurrentSecondKillSkus")
	R getCurrentSecondKillSkus();

	@GetMapping(value = "/sku/secondKill/{skuId}")
	R getSkuSecondKillInfo(@PathVariable("skuId") Long skuId);
}
