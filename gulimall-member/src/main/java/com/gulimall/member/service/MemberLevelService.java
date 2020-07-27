package com.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.member.entity.MemberLevelEntity;
import com.gulimall.service.utils.PageUtils;

import java.util.Map;

/**
 * 会员等级
 *
 * @author aqiang9
 * @email ${email}
 * @date 2020-07-27 10:19:25
 */
public interface MemberLevelService extends IService<MemberLevelEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

