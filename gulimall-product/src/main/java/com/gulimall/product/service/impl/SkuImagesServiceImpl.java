package com.gulimall.product.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.product.dao.SkuImagesDao;
import com.gulimall.product.entity.SkuImagesEntity;
import com.gulimall.product.service.SkuImagesService;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.service.utils.Query;

@Service("skuImagesService")
public class SkuImagesServiceImpl extends ServiceImpl<SkuImagesDao, SkuImagesEntity> implements SkuImagesService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<SkuImagesEntity> page = this.page(new Query<SkuImagesEntity>().getPage(params), new QueryWrapper<SkuImagesEntity>());

		return new PageUtils(page);
	}

	@Override
	public List<SkuImagesEntity> getImagesBySkuId(Long skuId) {
		SkuImagesDao imagesDao = this.baseMapper;
		List<SkuImagesEntity> images = imagesDao.selectList(new QueryWrapper<SkuImagesEntity>().eq("sku_id", skuId));
		return images;

	}

}