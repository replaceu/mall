package com.gulimall.ware.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gulimall.ware.entity.WareInfoEntity;

/**
 * 仓库信息
 */
@Mapper
public interface WareInfoDao extends BaseMapper<WareInfoEntity> {

}
