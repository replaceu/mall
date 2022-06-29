package com.gulimall.product.controller;

import java.math.BigDecimal;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gulimall.common.to.SkuInfoTo;
import com.gulimall.common.utils.R;
import com.gulimall.product.convert.SkuConvert;
import com.gulimall.product.entity.SkuInfoEntity;
import com.gulimall.product.service.SkuInfoService;
import com.gulimall.product.vo.SkuPageVo;
import com.gulimall.service.utils.PageUtils;

/**
 * sku信息
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-10 11:26:28
 */
@RestController
@RequestMapping("/product/skuinfo")
public class SkuInfoController {
	@Autowired
	private SkuInfoService skuInfoService;

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public R list(SkuPageVo params) {
		PageUtils page = skuInfoService.queryPageOnCondition(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@GetMapping("/info/{skuId}")
	public R getSkuInfo(@PathVariable("skuId") Long skuId) {
		SkuInfoEntity skuInfo = skuInfoService.getById(skuId);
		// 转换为 to
		SkuInfoTo skuInfoTo = SkuConvert.INSTANCE.entity2to(skuInfo);
		return R.ok().setData(skuInfoTo);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public R save(@RequestBody SkuInfoEntity skuInfo) {
		skuInfoService.save(skuInfo);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public R update(@RequestBody SkuInfoEntity skuInfo) {
		skuInfoService.updateById(skuInfo);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public R delete(@RequestBody Long[] skuIds) {
		skuInfoService.removeByIds(Arrays.asList(skuIds));

		return R.ok();
	}

	@GetMapping("/{skuId}/price")
	public BigDecimal getPrice(@PathVariable("skuId") Long skuId) {
		SkuInfoEntity toGetPrice = skuInfoService.getPriceBySkuId(skuId);
		return toGetPrice.getPrice();
	}

}
