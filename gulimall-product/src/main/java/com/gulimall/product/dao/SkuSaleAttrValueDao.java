package com.gulimall.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gulimall.product.entity.SkuSaleAttrValueEntity;
import com.gulimall.product.vo.SkuItemSaleAttrVo;

/**
 * sku销售属性&值
 * 
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-10 11:26:28
 */
@Mapper
public interface SkuSaleAttrValueDao extends BaseMapper<SkuSaleAttrValueEntity> {

	List<SkuItemSaleAttrVo> selectSaleAttrBySpuId(@Param("spuId") Long spuId);

	List<String> selectSkuSaleAttrValueAsStringList(@Param("skuId") Long skuId);

	List<SkuItemSaleAttrVo> getSaleAttrsBySpuId(Long spuId);
}
