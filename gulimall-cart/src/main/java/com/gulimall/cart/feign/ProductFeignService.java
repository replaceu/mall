package com.gulimall.cart.feign;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gulimall.common.constant.ServiceNameConstant;
import com.gulimall.common.to.SkuInfoTo;
import com.gulimall.common.utils.CommonResult;

/**
 * @author aqiang9  2020-09-10
 */
@FeignClient(ServiceNameConstant.PRODUCT_SERVICE_NAME)
public interface ProductFeignService {
	@GetMapping("/product/skuinfo/info/{skuId}")
	CommonResult<SkuInfoTo> getSkuInfo(@PathVariable("skuId") Long skuId);

	@GetMapping("/product/skusaleattrvalue/stringlist/{skuId}")
	List<String> getSkuSaleAttrValues(@PathVariable("skuId") Long skuId);

	@GetMapping("/product/skuinfo/{skuId}/price")
	BigDecimal getPrice(@PathVariable("skuId") Long skuId);
}
