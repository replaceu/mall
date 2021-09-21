package com.gulimall.order.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gulimall.common.utils.R;
import com.gulimall.order.entity.RefundInfoEntity;
import com.gulimall.order.service.RefundInfoService;
import com.gulimall.service.utils.PageUtils;

/**
 * 退款信息
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-09 10:01:26
 */
@RestController
@RequestMapping("order/refundinfo")
public class RefundInfoController {
	@Autowired
	private RefundInfoService refundInfoService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = refundInfoService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Long id) {
		RefundInfoEntity refundInfo = refundInfoService.getById(id);

		return R.ok().put("refundInfo", refundInfo);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public R save(@RequestBody RefundInfoEntity refundInfo) {
		refundInfoService.save(refundInfo);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public R update(@RequestBody RefundInfoEntity refundInfo) {
		refundInfoService.updateById(refundInfo);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public R delete(@RequestBody Long[] ids) {
		refundInfoService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
