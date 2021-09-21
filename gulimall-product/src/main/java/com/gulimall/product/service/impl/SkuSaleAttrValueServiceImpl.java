package com.gulimall.product.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.product.dao.SkuSaleAttrValueDao;
import com.gulimall.product.entity.SkuSaleAttrValueEntity;
import com.gulimall.product.service.SkuSaleAttrValueService;
import com.gulimall.product.vo.SkuItemSaleAttrVo;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.service.utils.Query;

@Service("skuSaleAttrValueService")
public class SkuSaleAttrValueServiceImpl extends ServiceImpl<SkuSaleAttrValueDao, SkuSaleAttrValueEntity> implements SkuSaleAttrValueService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<SkuSaleAttrValueEntity> page = this.page(new Query<SkuSaleAttrValueEntity>().getPage(params), new QueryWrapper<SkuSaleAttrValueEntity>());

		return new PageUtils(page);
	}

	@Override
	public List<SkuItemSaleAttrVo> getSaleAttrBySpuId(Long spuId) {
		return baseMapper.selectSaleAttrBySpuId(spuId);
	}

	@Override
	public List<String> getSkuSaleAttrValueAsStringList(Long skuId) {

		return baseMapper.selectSkuSaleAttrValueAsStringList(skuId);

	}

	/**
	 * 传入spuId,
	 * 分析当前spu有多少个sku,所有sku涉及到的属性组合
	 * @param spuId
	 * @return
	 */
	@Override
	public List<SkuItemSaleAttrVo> getSaleAttrBySpuIdByCarter(Long spuId) {

		List<SkuItemSaleAttrVo> retList = new ArrayList<>();
		SkuSaleAttrValueDao skuSaleAttrValueDao = this.baseMapper;
		retList = skuSaleAttrValueDao.getSaleAttrsBySpuId(spuId);

		return retList;
	}

}