package com.gulimall.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gulimall.common.utils.R;
import com.gulimall.product.entity.SkuImagesEntity;
import com.gulimall.product.service.SkuImagesService;
import com.gulimall.service.utils.PageUtils;

/**
 * sku图片
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-10 11:26:28
 */
@RestController
@RequestMapping("product/skuimages")
public class SkuImagesController {
	@Autowired
	private SkuImagesService skuImagesService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = skuImagesService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Long id) {
		SkuImagesEntity skuImages = skuImagesService.getById(id);

		return R.ok().put("skuImages", skuImages);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public R save(@RequestBody SkuImagesEntity skuImages) {
		skuImagesService.save(skuImages);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public R update(@RequestBody SkuImagesEntity skuImages) {
		skuImagesService.updateById(skuImages);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public R delete(@RequestBody Long[] ids) {
		skuImagesService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
