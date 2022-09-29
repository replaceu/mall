package com.gulimall.secondKill.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gulimall.common.utils.R;
import com.gulimall.secondKill.constants.SecondKillConstants;
import com.gulimall.secondKill.service.SecondKillService;
import com.gulimall.secondKill.to.SecondKillSkuRedisTo;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = SecondKillConstants.mallSecondKill, produces = "application/json;charset=UTF-8")
@Api(value = "mallSecondKill", description = "用户秒杀")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class SecondKillController {
	@Autowired
	SecondKillService secondKillService;

	/**
	 * 返回当前时间可以参与的秒杀商品信息
	 */

	@GetMapping(value = SecondKillConstants.MappingConstants.getSecondKillSkuList)
	public R getSecondKillSkuList() {
		List<SecondKillSkuRedisTo> secondKillSkuRedisList = secondKillService.getSecondKillSkuList();
		return R.ok().setData(secondKillSkuRedisList);
	}

	@GetMapping(value = SecondKillConstants.MappingConstants.getSkuSecondKillInfo)
	public R getSkuSecondKillInfo(@PathVariable("skuId") Long skuId) {
		SecondKillSkuRedisTo retTo = secondKillService.getSkuSecondKillInfo(skuId);
		return R.ok().setData(retTo);
	}

	@GetMapping(value = SecondKillConstants.MappingConstants.mallUserDoSecondKill)
	public R secondKill(@RequestParam("killId") String killId, @RequestParam("key") String key, @RequestParam("num") Integer num) {
		//1.判断是否登录（登陆拦截器已经自动处理）
		//2.秒杀成功返回一个orderSn
		String orderSn = secondKillService.mallUserDoSecondKill(killId, key, num);
		return R.ok().setData(orderSn);
	}

}
