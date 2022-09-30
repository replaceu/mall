package com.gulimall.coupon.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gulimall.common.utils.R;
import com.gulimall.coupon.entity.HomeAdvEntity;
import com.gulimall.coupon.service.HomeAdvService;
import com.gulimall.service.utils.PageUtils;

/**
 * 首页轮播广告
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-09 09:23:34
 */
@RestController
@RequestMapping("coupon/homeadv")
public class HomeAdvController {
	@Autowired
	private HomeAdvService homeAdvService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = homeAdvService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Long id) {
		HomeAdvEntity homeAdv = homeAdvService.getById(id);

		return R.ok().put("homeAdv", homeAdv);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public R save(@RequestBody HomeAdvEntity homeAdv) {
		homeAdvService.save(homeAdv);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public R update(@RequestBody HomeAdvEntity homeAdv) {
		homeAdvService.updateById(homeAdv);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public R delete(@RequestBody Long[] ids) {
		homeAdvService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
