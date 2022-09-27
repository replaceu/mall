package com.gulimall.order.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gulimall.common.utils.R;
import com.gulimall.order.entity.OrderItemEntity;
import com.gulimall.order.service.OrderItemService;
import com.gulimall.service.utils.PageUtils;

/**
 * 订单项信息
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-09 10:01:26
 */
@RestController
@RequestMapping("order/orderitem")
public class OrderItemController {
	@Autowired
	private OrderItemService orderItemService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")

	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = orderItemService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")

	public R info(@PathVariable("id") Long id) {
		OrderItemEntity orderItem = orderItemService.getById(id);

		return R.ok().put("orderItem", orderItem);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")

	public R save(@RequestBody OrderItemEntity orderItem) {
		orderItemService.save(orderItem);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")

	public R update(@RequestBody OrderItemEntity orderItem) {
		orderItemService.updateById(orderItem);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")

	public R delete(@RequestBody Long[] ids) {
		orderItemService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
