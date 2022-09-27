package com.gulimall.order.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gulimall.common.utils.R;
import com.gulimall.order.entity.OrderReturnApplyEntity;
import com.gulimall.order.service.OrderReturnApplyService;
import com.gulimall.service.utils.PageUtils;

/**
 * 订单退货申请
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-09 10:01:26
 */
@RestController
@RequestMapping("order/orderreturnapply")
public class OrderReturnApplyController {
	@Autowired
	private OrderReturnApplyService orderReturnApplyService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = orderReturnApplyService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Long id) {
		OrderReturnApplyEntity orderReturnApply = orderReturnApplyService.getById(id);

		return R.ok().put("orderReturnApply", orderReturnApply);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public R save(@RequestBody OrderReturnApplyEntity orderReturnApply) {
		orderReturnApplyService.save(orderReturnApply);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public R update(@RequestBody OrderReturnApplyEntity orderReturnApply) {
		orderReturnApplyService.updateById(orderReturnApply);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public R delete(@RequestBody Long[] ids) {
		orderReturnApplyService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
