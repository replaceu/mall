package com.gulimall.coupon.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gulimall.common.utils.R;
import com.gulimall.coupon.entity.SecondKillSessionEntity;
import com.gulimall.coupon.service.SecondKillSessionService;
import com.gulimall.service.utils.PageUtils;

/**
 * 秒杀活动场次
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-09 09:23:34
 */
@RestController
@RequestMapping("coupon/seckillsession")
public class SecondKillSessionController {
	@Autowired
	private SecondKillSessionService secondKillSessionService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = secondKillSessionService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Long id) {
		SecondKillSessionEntity seckillSession = secondKillSessionService.getById(id);

		return R.ok().put("seckillSession", seckillSession);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public R save(@RequestBody SecondKillSessionEntity seckillSession) {
		secondKillSessionService.save(seckillSession);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public R update(@RequestBody SecondKillSessionEntity seckillSession) {
		secondKillSessionService.updateById(seckillSession);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public R delete(@RequestBody Long[] ids) {
		secondKillSessionService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

	/**
	 * 获取近3天的秒杀信息
	 */

	public R getLasts3DaySession() {
		List<SecondKillSessionEntity> secondKillSessionEntities = secondKillSessionService.getLasts3DaySession();

		return R.ok().setData(secondKillSessionEntities);
	}
}
