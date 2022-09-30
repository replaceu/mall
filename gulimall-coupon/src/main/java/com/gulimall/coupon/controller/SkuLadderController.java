package com.gulimall.coupon.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gulimall.common.utils.R;
import com.gulimall.coupon.entity.SkuLadderEntity;
import com.gulimall.coupon.service.SkuLadderService;
import com.gulimall.service.utils.PageUtils;

/**
 * 商品阶梯价格
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-09 09:23:34
 */
@RestController
@RequestMapping("coupon/skuladder")
public class SkuLadderController {
	@Autowired
	private SkuLadderService skuLadderService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = skuLadderService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Long id) {
		SkuLadderEntity skuLadder = skuLadderService.getById(id);

		return R.ok().put("skuLadder", skuLadder);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public R save(@RequestBody SkuLadderEntity skuLadder) {
		skuLadderService.save(skuLadder);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public R update(@RequestBody SkuLadderEntity skuLadder) {
		skuLadderService.updateById(skuLadder);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public R delete(@RequestBody Long[] ids) {
		skuLadderService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
