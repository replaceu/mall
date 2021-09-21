package com.gulimall.member.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.member.entity.MemberReceiveAddressEntity;
import com.gulimall.service.utils.PageUtils;

/**
 * 会员收货地址
 *
 * @author aqiang9
 * @email ${email}
 * @date 2020-07-27 10:19:25
 */
public interface MemberReceiveAddressService extends IService<MemberReceiveAddressEntity> {

	PageUtils queryPage(Map<String, Object> params);

	List<MemberReceiveAddressEntity> getAddressesByMemberId(Long memberId);
}
