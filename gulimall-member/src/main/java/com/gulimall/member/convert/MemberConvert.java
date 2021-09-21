package com.gulimall.member.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.gulimall.common.vo.MemberRespVo;
import com.gulimall.member.entity.MemberEntity;
import com.gulimall.member.vo.MemberRegisterVo;

/**
 * @author aqiang9  2020-09-02
 */
@Mapper
public interface MemberConvert {
	MemberConvert INSTANCE = Mappers.getMapper(MemberConvert.class);

	MemberEntity vo2entity(MemberRegisterVo registerVo);

	MemberRespVo entity2vo(MemberEntity memberEntity);

}
