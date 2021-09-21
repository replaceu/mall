package com.gulimall.member.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.member.dao.MemberReceiveAddressDao;
import com.gulimall.member.entity.MemberReceiveAddressEntity;
import com.gulimall.member.service.MemberReceiveAddressService;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.service.utils.Query;

@Service("memberReceiveAddressService")
public class MemberReceiveAddressServiceImpl extends ServiceImpl<MemberReceiveAddressDao, MemberReceiveAddressEntity> implements MemberReceiveAddressService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<MemberReceiveAddressEntity> page = this.page(new Query<MemberReceiveAddressEntity>().getPage(params), new QueryWrapper<MemberReceiveAddressEntity>());

		return new PageUtils(page);
	}

	@Override
	public List<MemberReceiveAddressEntity> getAddressesByMemberId(Long memberId) {

		MemberReceiveAddressDao addressDao = this.baseMapper;
		List<MemberReceiveAddressEntity> addresses = addressDao.selectList(new QueryWrapper<MemberReceiveAddressEntity>().eq("member_id", memberId));
		return addresses;
	}

}