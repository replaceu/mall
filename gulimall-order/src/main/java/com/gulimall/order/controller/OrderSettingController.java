package com.gulimall.order.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gulimall.common.utils.R;
import com.gulimall.order.entity.OrderSettingEntity;
import com.gulimall.order.service.OrderSettingService;
import com.gulimall.service.utils.PageUtils;

/**
 * 订单配置信息
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-09 10:01:26
 */
@RestController
@RequestMapping("order/ordersetting")
public class OrderSettingController {
	@Autowired
	private OrderSettingService orderSettingService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = orderSettingService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Long id) {
		OrderSettingEntity orderSetting = orderSettingService.getById(id);

		return R.ok().put("orderSetting", orderSetting);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public R save(@RequestBody OrderSettingEntity orderSetting) {
		orderSettingService.save(orderSetting);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public R update(@RequestBody OrderSettingEntity orderSetting) {
		orderSettingService.updateById(orderSetting);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public R delete(@RequestBody Long[] ids) {
		orderSettingService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
