package com.gulimall.member.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gulimall.common.utils.R;
import com.gulimall.member.entity.MemberReceiveAddressEntity;
import com.gulimall.member.service.MemberReceiveAddressService;
import com.gulimall.service.utils.PageUtils;

/**
 * 会员收货地址
 *
 * @author aqiang9
 * @email ${email}
 * @date 2020-07-27 10:19:25
 */
@RestController
@RequestMapping("member/memberreceiveaddress")
public class MemberReceiveAddressController {
	@Autowired
	private MemberReceiveAddressService memberReceiveAddressService;

	//通过用户的id得到用户地址列表
	@GetMapping("/{memberId}/addresses")
	public List<MemberReceiveAddressEntity> getAddress(@PathVariable("memberId") Long memberId) {
		return memberReceiveAddressService.getAddressesByMemberId(memberId);

	}

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = memberReceiveAddressService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Long id) {
		MemberReceiveAddressEntity memberReceiveAddress = memberReceiveAddressService.getById(id);

		return R.ok().put("memberReceiveAddress", memberReceiveAddress);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public R save(@RequestBody MemberReceiveAddressEntity memberReceiveAddress) {
		memberReceiveAddressService.save(memberReceiveAddress);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public R update(@RequestBody MemberReceiveAddressEntity memberReceiveAddress) {
		memberReceiveAddressService.updateById(memberReceiveAddress);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public R delete(@RequestBody Long[] ids) {
		memberReceiveAddressService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
