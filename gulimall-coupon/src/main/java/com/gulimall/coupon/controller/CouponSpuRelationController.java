package com.gulimall.coupon.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gulimall.common.utils.R;
import com.gulimall.coupon.entity.CouponSpuRelationEntity;
import com.gulimall.coupon.service.CouponSpuRelationService;
import com.gulimall.service.utils.PageUtils;

/**
 * 优惠券与产品关联
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-09 09:23:34
 */
@RestController
@RequestMapping("coupon/couponspurelation")
public class CouponSpuRelationController {
	@Autowired
	private CouponSpuRelationService couponSpuRelationService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = couponSpuRelationService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Long id) {
		CouponSpuRelationEntity couponSpuRelation = couponSpuRelationService.getById(id);

		return R.ok().put("couponSpuRelation", couponSpuRelation);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public R save(@RequestBody CouponSpuRelationEntity couponSpuRelation) {
		couponSpuRelationService.save(couponSpuRelation);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public R update(@RequestBody CouponSpuRelationEntity couponSpuRelation) {
		couponSpuRelationService.updateById(couponSpuRelation);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public R delete(@RequestBody Long[] ids) {
		couponSpuRelationService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
