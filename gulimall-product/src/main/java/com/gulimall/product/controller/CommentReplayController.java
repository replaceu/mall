package com.gulimall.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gulimall.common.utils.R;
import com.gulimall.product.entity.CommentReplayEntity;
import com.gulimall.product.service.CommentReplayService;
import com.gulimall.service.utils.PageUtils;

/**
 * 商品评价回复关系
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-10 11:26:28
 */
@RestController
@RequestMapping("product/commentreplay")
public class CommentReplayController {
	@Autowired
	private CommentReplayService commentReplayService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = commentReplayService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Long id) {
		CommentReplayEntity commentReplay = commentReplayService.getById(id);

		return R.ok().put("commentReplay", commentReplay);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public R save(@RequestBody CommentReplayEntity commentReplay) {
		commentReplayService.save(commentReplay);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public R update(@RequestBody CommentReplayEntity commentReplay) {
		commentReplayService.updateById(commentReplay);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public R delete(@RequestBody Long[] ids) {
		commentReplayService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
