package com.gulimall.ware.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gulimall.ware.entity.PurchaseEntity;

/**
 * 采购信息
 */
@Mapper
public interface PurchaseDao extends BaseMapper<PurchaseEntity> {

}
