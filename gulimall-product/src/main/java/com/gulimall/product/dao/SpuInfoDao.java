package com.gulimall.product.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gulimall.product.entity.SpuInfoEntity;

/**
 * spu信息
 * 
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-10 11:26:28
 */
@Mapper
public interface SpuInfoDao extends BaseMapper<SpuInfoEntity> {

	void upProductStatus(Long spuId);
}
