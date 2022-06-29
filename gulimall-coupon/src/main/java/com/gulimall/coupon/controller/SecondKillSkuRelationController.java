package com.gulimall.coupon.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gulimall.common.utils.R;
import com.gulimall.coupon.entity.SecondKillSkuRelationEntity;
import com.gulimall.coupon.service.SecondKillSkuRelationService;
import com.gulimall.service.utils.PageUtils;

/**
 * 秒杀活动商品关联
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-09 09:23:34
 */
@RestController
@RequestMapping("coupon/SecondKillskurelation")
public class SecondKillSkuRelationController {
	@Autowired
	private SecondKillSkuRelationService secondKillSkuRelationService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = secondKillSkuRelationService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Long id) {
		SecondKillSkuRelationEntity SecondKillSkuRelation = secondKillSkuRelationService.getById(id);

		return R.ok().put("SecondKillSkuRelation", SecondKillSkuRelation);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")

	public R save(@RequestBody SecondKillSkuRelationEntity SecondKillSkuRelation) {
		secondKillSkuRelationService.save(SecondKillSkuRelation);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")

	public R update(@RequestBody SecondKillSkuRelationEntity SecondKillSkuRelation) {
		secondKillSkuRelationService.updateById(SecondKillSkuRelation);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public R delete(@RequestBody Long[] ids) {
		secondKillSkuRelationService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
