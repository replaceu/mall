package com.gulimall.order.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gulimall.common.utils.R;
import com.gulimall.order.entity.OrderReturnReasonEntity;
import com.gulimall.order.service.OrderReturnReasonService;
import com.gulimall.service.utils.PageUtils;

/**
 * 退货原因
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-09 10:01:26
 */
@RestController
@RequestMapping("order/orderreturnreason")
public class OrderReturnReasonController {
	@Autowired
	private OrderReturnReasonService orderReturnReasonService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = orderReturnReasonService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Long id) {
		OrderReturnReasonEntity orderReturnReason = orderReturnReasonService.getById(id);

		return R.ok().put("orderReturnReason", orderReturnReason);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public R save(@RequestBody OrderReturnReasonEntity orderReturnReason) {
		orderReturnReasonService.save(orderReturnReason);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public R update(@RequestBody OrderReturnReasonEntity orderReturnReason) {
		orderReturnReasonService.updateById(orderReturnReason);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public R delete(@RequestBody Long[] ids) {
		orderReturnReasonService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
