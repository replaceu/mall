package com.gulimall.ware.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gulimall.common.to.SkuHasStockTo;
import com.gulimall.common.utils.CommonResult;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.ware.entity.WareSkuEntity;
import com.gulimall.ware.service.WareSkuService;
import com.gulimall.ware.vo.WareSkuLockVo;
import com.gulimall.ware.vo.WareSkuPageVo;

/**
*商品库存
*/
@RestController
@RequestMapping("/ware/waresku")
public class WareSkuController {
	@Autowired
	private WareSkuService wareSkuService;

	@PostMapping("/lock/order")
	public CommonResult orderLockStock(@RequestBody WareSkuLockVo wareSkuLock) {
		Boolean result = wareSkuService.orderLockStock(wareSkuLock);
		if (result) {
			return CommonResult.ok(result);
		} else {
			return CommonResult.fail(21000, "没有库存");
		}

	}

	/**
	*查询商品是否有库存
	*/
	@PostMapping("/has/stock")
	public CommonResult<List<SkuHasStockTo>> hasStock(@RequestBody List<Long> skuIds) {
		List<SkuHasStockTo> stockTos = wareSkuService.getSkuHasStock(skuIds);
		return CommonResult.ok(stockTos);
	}

	/**
	*列表
	*/
	@GetMapping("/list")
	//@RequiresPermissions("ware:waresku:list")
	public CommonResult list(WareSkuPageVo params) {
		PageUtils page = wareSkuService.queryPage(params);
		return CommonResult.ok(page);
	}

	/**
	*信息
	*/
	@GetMapping("/info/{id}")
	public CommonResult<WareSkuEntity> info(@PathVariable("id") Long id) {
		WareSkuEntity wareSku = wareSkuService.getById(id);

		return CommonResult.ok(wareSku);
	}

	/**
	*保存
	*/
	@PostMapping("/save")
	public CommonResult save(@RequestBody WareSkuEntity wareSku) {
		wareSkuService.save(wareSku);

		return CommonResult.ok();
	}

	/**
	*修改
	*/
	@PutMapping("/update")
	public CommonResult update(@RequestBody WareSkuEntity wareSku) {
		wareSkuService.updateById(wareSku);

		return CommonResult.ok();
	}

	/**
	*删除
	*/
	@DeleteMapping("/delete")
	public CommonResult delete(@RequestBody Long[] ids) {
		wareSkuService.removeByIds(Arrays.asList(ids));
		return CommonResult.ok();
	}

}
