package com.gulimall.ware.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gulimall.common.utils.CommonResult;
import com.gulimall.common.vo.PageVo;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.ware.entity.PurchaseEntity;
import com.gulimall.ware.service.PurchaseService;
import com.gulimall.ware.vo.PurchaseDoneVo;
import com.gulimall.ware.vo.PurchaseMergeVo;
import com.gulimall.ware.vo.PurchasePageVo;

/**
 * 采购信息
 */
@RestController
@RequestMapping("/ware/purchase")
public class PurchaseController {
	@Autowired
	private PurchaseService purchaseService;

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public CommonResult<PageUtils> list(PurchasePageVo params) {
		PageUtils page = purchaseService.queryPage(params);

		return CommonResult.ok(page);
	}

	@GetMapping("/unreceive/list")
	public CommonResult unReceiveList(PageVo params) {
		PageUtils page = purchaseService.queryUnReceivePage(params);
		return CommonResult.ok().data(page);
	}

	/**
	 * 合并采购单
	 */
	@PostMapping("/merge")
	public CommonResult mergePurchase(@RequestBody PurchaseMergeVo mergeVo) {
		purchaseService.mergePurchase(mergeVo);
		return CommonResult.ok();
	}

	/**
	 * 领取采购单
	 */
	@PostMapping("/received")
	public CommonResult receivedPurchase(@RequestBody List<Long> ids) {
		purchaseService.receivedPurchase(ids);
		return CommonResult.ok();
	}

	/**
	 * 完成采购单
	 */
	@PostMapping("/done")
	public CommonResult finish(@RequestBody PurchaseDoneVo doneVo) {
		purchaseService.donePurchase(doneVo);
		return CommonResult.ok();
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public CommonResult info(@PathVariable("id") Long id) {
		PurchaseEntity purchase = purchaseService.getById(id);
		return CommonResult.ok(purchase);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public CommonResult save(@RequestBody PurchaseEntity purchase) {
		purchaseService.save(purchase);

		return CommonResult.ok();
	}

	/**
	 * 修改
	 */
	@PutMapping("/update")
	public CommonResult<Object> update(@RequestBody PurchaseEntity purchase) {
		purchaseService.updateById(purchase);
		return CommonResult.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public CommonResult delete(@RequestBody Long[] ids) {
		purchaseService.removeByIds(Arrays.asList(ids));
		return CommonResult.ok();
	}
}
