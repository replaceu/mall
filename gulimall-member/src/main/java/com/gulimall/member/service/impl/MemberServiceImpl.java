package com.gulimall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.common.exception.BusinessException;
import com.gulimall.member.dao.MemberDao;
import com.gulimall.member.entity.MemberEntity;
import com.gulimall.member.entity.MemberLevelEntity;
import com.gulimall.member.exception.MemberErrorCode;
import com.gulimall.member.service.MemberLevelService;
import com.gulimall.member.service.MemberService;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.service.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    @Autowired
    MemberLevelService memberLevelService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void register(MemberEntity memberEntity) throws BusinessException {
// 唯一性判断
        checkPhoneUnique(memberEntity.getMobile());
        checkUsernameUnique(memberEntity.getUsername());

        // 设置数据
        // 1、设置默认等级
        MemberLevelEntity defaultLevel = memberLevelService.getDefaultLevel();
        if (defaultLevel != null) {
            memberEntity.setLevelId(defaultLevel.getId());
        }
        memberEntity.setPassword(passwordEncoder.encode(memberEntity.getPassword()));
    }

    @Override
    public void checkPhoneUnique(String phone) throws BusinessException {
        Integer count = baseMapper.selectCount(new LambdaQueryWrapper<MemberEntity>().eq(MemberEntity::getMobile, phone));
        if (count > 0) {
            throw new BusinessException(MemberErrorCode.PHONE_EXIST_EXCEPTION);
        }
    }

    @Override
    public void checkUsernameUnique(String username) throws BusinessException {
        Integer count = baseMapper.selectCount(new LambdaQueryWrapper<MemberEntity>().eq(MemberEntity::getUsername, username));
        if (count > 0) {
            throw new BusinessException(MemberErrorCode.USERNAME_EXIST_EXCEPTION);
        }
    }

    @Override
    public MemberEntity login(String loginAccount, String password) {

        MemberEntity memberEntity = baseMapper.selectOne(new LambdaQueryWrapper<MemberEntity>().eq(MemberEntity::getUsername, loginAccount).or().eq(MemberEntity::getMobile, loginAccount));
        if (memberEntity != null && passwordEncoder.matches(  password , memberEntity.getPassword()  ) ) {
            return memberEntity ;
        }
// 登陆失败
        return null ;

    }
}