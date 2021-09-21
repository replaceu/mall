package com.gulimall.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gulimall.product.entity.AttrEntity;

/**
 * 商品属性
 * 
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-10 11:26:28
 */
@Mapper
public interface AttrDao extends BaseMapper<AttrEntity> {

	List<Long> selectSearchAttrs(@Param("attrIds") List<Long> attrIds);

	List<Long> selectSearchAttrIds(@Param("attrIds") List<Long> attrIds);
}
