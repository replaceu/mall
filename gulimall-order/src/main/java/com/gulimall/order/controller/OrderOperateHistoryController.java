package com.gulimall.order.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gulimall.common.utils.R;
import com.gulimall.order.entity.OrderOperateHistoryEntity;
import com.gulimall.order.service.OrderOperateHistoryService;
import com.gulimall.service.utils.PageUtils;

/**
 * 订单操作历史记录
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-09 10:01:26
 */
@RestController
@RequestMapping("order/orderoperatehistory")
public class OrderOperateHistoryController {
	@Autowired
	private OrderOperateHistoryService orderOperateHistoryService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = orderOperateHistoryService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Long id) {
		OrderOperateHistoryEntity orderOperateHistory = orderOperateHistoryService.getById(id);

		return R.ok().put("orderOperateHistory", orderOperateHistory);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public R save(@RequestBody OrderOperateHistoryEntity orderOperateHistory) {
		orderOperateHistoryService.save(orderOperateHistory);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public R update(@RequestBody OrderOperateHistoryEntity orderOperateHistory) {
		orderOperateHistoryService.updateById(orderOperateHistory);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public R delete(@RequestBody Long[] ids) {
		orderOperateHistoryService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
