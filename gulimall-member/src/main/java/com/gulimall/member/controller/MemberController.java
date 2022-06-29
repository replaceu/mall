package com.gulimall.member.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gulimall.common.utils.CommonResult;
import com.gulimall.common.utils.R;
import com.gulimall.common.vo.MemberRespVo;
import com.gulimall.member.convert.MemberConvert;
import com.gulimall.member.entity.MemberEntity;
import com.gulimall.member.exception.MemberErrorCode;
import com.gulimall.member.service.MemberService;
import com.gulimall.member.vo.MemberLoginVo;
import com.gulimall.member.vo.MemberRegisterVo;
import com.gulimall.member.vo.SocialUser;
import com.gulimall.service.utils.PageUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 会员
 *
 * @author aqiang9
 * @email ${email}
 * @date 2020-07-27 10:19:25
 */
@RestController
@RequestMapping("member/member")
@Slf4j
public class MemberController {
	@Autowired
	private MemberService memberService;

	/**
	 * 用户注册
	 *
	 * @param registerVo 注册的用户数据
	 * @return
	 */
	@PostMapping("/register")
	public CommonResult<MemberRespVo> register(@RequestBody MemberRegisterVo registerVo) {
		MemberEntity memberEntity = MemberConvert.INSTANCE.vo2entity(registerVo);
		memberEntity.setNickname(registerVo.getUsername());
		memberService.register(memberEntity);
		//log.info("用户注册成功, {} ", memberEntity);
		MemberRespVo memberRespVo = MemberConvert.INSTANCE.entity2vo(memberEntity);
		return CommonResult.ok(memberRespVo);
	}

	/**
	 * 登陆
	 *
	 * @param loginVo 登陆信息
	 */
	@PostMapping("/login")
	public CommonResult<MemberRespVo> login(@RequestBody MemberLoginVo loginVo) {
		MemberEntity memberEntity = memberService.login(loginVo.getLoginAccount(), loginVo.getPassword());
		if (memberEntity == null) { return CommonResult.fail(MemberErrorCode.LOGIN_PASSWORD_INVALID_EXCEPTION); }
		//log.info("用户登录信息, {} ", memberEntity);
		MemberRespVo MemberRespVo = MemberConvert.INSTANCE.entity2vo(memberEntity);
		return CommonResult.ok(MemberRespVo);
	}

	/**
	 * 社交登陆
	 *
	 * @param socialUser 社交登陆信息
	 */
	@PostMapping("/oauth2/login")
	public CommonResult<MemberRespVo> oauthLogin(@RequestBody SocialUser socialUser) {

		MemberEntity memberEntity = memberService.login(socialUser);
		if (memberEntity == null) { return CommonResult.fail(MemberErrorCode.LOGIN_PASSWORD_INVALID_EXCEPTION); }

		MemberRespVo MemberRespVo = MemberConvert.INSTANCE.entity2vo(memberEntity);
		return CommonResult.ok(MemberRespVo);
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = memberService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	//    @RequiresPermissions("member:member:info")
	public R info(@PathVariable("id") Long id) {
		MemberEntity member = memberService.getById(id);

		return R.ok().put("member", member);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	//    @RequiresPermissions("member:member:save")
	public R save(@RequestBody MemberEntity member) {
		memberService.save(member);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	//    @RequiresPermissions("member:member:update")
	public R update(@RequestBody MemberEntity member) {
		memberService.updateById(member);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public R delete(@RequestBody Long[] ids) {
		memberService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
