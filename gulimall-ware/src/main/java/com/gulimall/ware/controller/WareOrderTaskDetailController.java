package com.gulimall.ware.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gulimall.common.utils.CommonResult;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.ware.entity.WareOrderTaskDetailEntity;
import com.gulimall.ware.service.WareOrderTaskDetailService;

/**
 * 库存工作明细单

 */
@RestController
@RequestMapping("ware/wareordertaskdetail")
public class WareOrderTaskDetailController {
	@Autowired
	private WareOrderTaskDetailService wareOrderTaskDetailService;

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public CommonResult list(@RequestParam Map<String, Object> params) {
		PageUtils page = wareOrderTaskDetailService.queryPage(params);

		return CommonResult.ok(page);
	}

	/**
	 * 信息
	 */
	@GetMapping("/info/{id}")
	public CommonResult<WareOrderTaskDetailEntity> info(@PathVariable("id") Long id) {
		WareOrderTaskDetailEntity wareOrderTaskDetail = wareOrderTaskDetailService.getById(id);
		return CommonResult.ok(wareOrderTaskDetail);
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public CommonResult save(@RequestBody WareOrderTaskDetailEntity wareOrderTaskDetail) {
		wareOrderTaskDetailService.save(wareOrderTaskDetail);

		return CommonResult.ok();
	}

	/**
	 * 修改
	 */
	@PutMapping("/update")
	public CommonResult update(@RequestBody WareOrderTaskDetailEntity wareOrderTaskDetail) {
		wareOrderTaskDetailService.updateById(wareOrderTaskDetail);

		return CommonResult.ok();
	}

	/**
	 * 删除
	 */
	@DeleteMapping("/delete")
	public CommonResult delete(@RequestBody Long[] ids) {
		wareOrderTaskDetailService.removeByIds(Arrays.asList(ids));
		return CommonResult.ok();
	}

}
