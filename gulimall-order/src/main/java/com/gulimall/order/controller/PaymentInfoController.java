package com.gulimall.order.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gulimall.common.utils.R;
import com.gulimall.order.entity.PaymentInfoEntity;
import com.gulimall.order.service.PaymentInfoService;
import com.gulimall.service.utils.PageUtils;

@RestController
@RequestMapping("order/paymentinfo")
public class PaymentInfoController {
	@Autowired
	private PaymentInfoService paymentInfoService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = paymentInfoService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Long id) {
		PaymentInfoEntity paymentInfo = paymentInfoService.getById(id);

		return R.ok().put("paymentInfo", paymentInfo);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public R save(@RequestBody PaymentInfoEntity paymentInfo) {
		paymentInfoService.save(paymentInfo);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public R update(@RequestBody PaymentInfoEntity paymentInfo) {
		paymentInfoService.updateById(paymentInfo);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public R delete(@RequestBody Long[] ids) {
		paymentInfoService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
