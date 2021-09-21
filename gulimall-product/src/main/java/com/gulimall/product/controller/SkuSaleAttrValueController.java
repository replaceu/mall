package com.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gulimall.common.utils.R;
import com.gulimall.product.entity.SkuSaleAttrValueEntity;
import com.gulimall.product.service.SkuSaleAttrValueService;
import com.gulimall.service.utils.PageUtils;

/**
 * sku销售属性&值
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-10 11:26:28
 */
@RestController
@RequestMapping("product/skusaleattrvalue")
public class SkuSaleAttrValueController {
	@Autowired
	private SkuSaleAttrValueService skuSaleAttrValueService;

	@GetMapping("/stringlist/{skuId}")
	public List<String> getSkuSaleAttrValues(@PathVariable("skuId") Long skuId) {
		return skuSaleAttrValueService.getSkuSaleAttrValueAsStringList(skuId);
	}

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = skuSaleAttrValueService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Long id) {
		SkuSaleAttrValueEntity skuSaleAttrValue = skuSaleAttrValueService.getById(id);

		return R.ok().put("skuSaleAttrValue", skuSaleAttrValue);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")

	public R save(@RequestBody SkuSaleAttrValueEntity skuSaleAttrValue) {
		skuSaleAttrValueService.save(skuSaleAttrValue);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")

	public R update(@RequestBody SkuSaleAttrValueEntity skuSaleAttrValue) {
		skuSaleAttrValueService.updateById(skuSaleAttrValue);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")

	public R delete(@RequestBody Long[] ids) {
		skuSaleAttrValueService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
