package com.gulimall.integral.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gulimall.integral.entity.IntegralUserFeeEntity;
import com.gulimall.integral.entity.IntegralUserFreeEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IntegralUserFreeDao extends BaseMapper<IntegralUserFreeEntity> {
}
