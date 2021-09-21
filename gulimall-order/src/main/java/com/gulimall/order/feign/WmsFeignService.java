package com.gulimall.order.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.gulimall.common.to.SkuHasStockTo;
import com.gulimall.common.utils.CommonResult;
import com.gulimall.common.utils.R;
import com.gulimall.order.vo.WareSkuLockVo;

/**
 * @author Carter
 * @date 2021-08-31 02:32
 * @description:
 * @version:
 */
@FeignClient("gulimall-ware")
public interface WmsFeignService {
	/**
	 *查询商品是否有库存
	 */
	@PostMapping("/ware/waresku/has/stock")
	CommonResult<List<SkuHasStockTo>> hasStock(@RequestBody List<Long> skuIds);

	/**
	 * 查询运费相关信息
	 * @param addressId
	 * @return
	 */
	@GetMapping("/ware/wareInfo/fare")
	R getFare(@RequestParam("addressId") Long addressId);

	@PostMapping("/ware/waresku/lock/order")
	CommonResult orderLockStock(@RequestBody WareSkuLockVo wareSkuLock);

}
