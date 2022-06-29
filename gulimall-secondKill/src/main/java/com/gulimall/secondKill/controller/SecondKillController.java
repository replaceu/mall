package com.gulimall.secondKill.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gulimall.common.utils.R;
import com.gulimall.secondKill.service.SecondKillService;
import com.gulimall.secondKill.to.SecondKillSkuRedisTo;

@RestController
public class SecondKillController {
	@Autowired
	SecondKillService secondKillService;

	/**
	 * 返回当前时间可以参与的秒杀商品信息
	 */

	@GetMapping(value = "/getCurrentSecondKillSkus")
	public R getCurrentSecondKillSkus() {
		List<SecondKillSkuRedisTo> secondKillSkuRedisList = secondKillService.getCurrentSecondKillSkus();
		return R.ok().setData(secondKillSkuRedisList);
	}

	@GetMapping(value = "/sku/secondKill/{skuId}")
	public R getSkuSecondKillInfo(@PathVariable("skuId") Long skuId) {
		SecondKillSkuRedisTo retTo = secondKillService.getSkuSecondKillInfo(skuId);
		return R.ok().setData(retTo);
	}

	@GetMapping(value = "/kill")
	public R secondKill(@RequestParam("killId") String killId, @RequestParam("key") String key, @RequestParam("num") Integer num) {
		//1.判断是否登录（登陆拦截器已经自动处理）
		//2.秒杀成功返回一个orderSn
		String orderSn = secondKillService.secondKill(killId, key, num);
		return R.ok().setData(orderSn);
	}

}
