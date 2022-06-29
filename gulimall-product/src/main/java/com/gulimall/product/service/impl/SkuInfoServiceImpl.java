package com.gulimall.product.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.common.utils.R;
import com.gulimall.product.dao.SkuInfoDao;
import com.gulimall.product.entity.SkuImagesEntity;
import com.gulimall.product.entity.SkuInfoEntity;
import com.gulimall.product.entity.SpuInfoDescEntity;
import com.gulimall.product.feign.SecondKillFeignService;
import com.gulimall.product.service.*;
import com.gulimall.product.vo.*;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.service.utils.QueryPage;

@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

	@Autowired
	SkuImagesService		skuImagesService;
	@Autowired
	SpuInfoDescService		spuInfoDescService;
	@Autowired
	AttrGroupService		attrGroupService;
	@Autowired
	SkuSaleAttrValueService	skuSaleAttrValueService;
	@Autowired
	SecondKillFeignService	secondKillFeignService;

	/**
	 * 线程池
	 */
	@Autowired
	ThreadPoolExecutor executor;

	@Override
	public PageUtils queryPageOnCondition(SkuPageVo params) {

		LambdaQueryWrapper<SkuInfoEntity> wrapper = new LambdaQueryWrapper<>();

		//封装区间
		if (params.getBrandId() != null && params.getBrandId() > 0) {
			wrapper.eq(SkuInfoEntity::getBrandId, params.getBrandId());
		}
		if (params.getCategoryId() != null && params.getCategoryId() > 0) {
			wrapper.eq(SkuInfoEntity::getCategoryId, params.getCategoryId());
		}

		if (!StringUtils.isEmpty(params.getKey())) {
			wrapper.and(w -> {
				w.eq(SkuInfoEntity::getSkuId, params.getKey()).or().like(SkuInfoEntity::getSkuName, params.getKey());
			});
		}
		if (params.getMax() != null && params.getMax().compareTo(BigDecimal.ZERO) > 0) {
			wrapper.le(SkuInfoEntity::getPrice, params.getMax());
		}
		if (params.getMin() != null) {
			wrapper.ge(SkuInfoEntity::getPrice, params.getMin());
		}

		IPage<SkuInfoEntity> spuInfoEntityIPage = baseMapper.selectPage(new QueryPage<SkuInfoEntity>().getPage(params), wrapper);
		return new PageUtils(spuInfoEntityIPage);

	}

	@Override
	public List<SkuInfoEntity> getSkusBySpuId(Long spuId) {
		return baseMapper.selectList(new LambdaQueryWrapper<SkuInfoEntity>().eq(SkuInfoEntity::getSpuId, spuId));
	}

	@Transactional
	@Override
	public void saveSkuInfo(SkuInfoEntity skuInfoEntity) {
		baseMapper.insert(skuInfoEntity);
	}

	@Override
	public SkuItemVo item(Long skuId) throws ExecutionException, InterruptedException {
		SkuItemVo retVo = new SkuItemVo();

		//使用异步编排加快页面速度
		CompletableFuture<SkuInfoEntity> skuInfoFuture = CompletableFuture.supplyAsync(() -> {
			//1、sku详细信息sku_info
			SkuInfoEntity skuInfo = getById(skuId);
			//三级分类Id
			Long categoryId = skuInfo.getCategoryId();
			Long spuId = skuInfo.getSpuId();
			retVo.setInfo(skuInfo);
			return skuInfo;
		}, executor);
		CompletableFuture<Void> saleAttrsFuture = skuInfoFuture.thenAcceptAsync((result) -> {
			//3、spu销售属性组合
			List<SkuItemSaleAttrVo> saleAttrs = skuSaleAttrValueService.getSaleAttrBySpuIdByCarter(result.getSpuId());
			retVo.setSaleAttr(saleAttrs);
		}, executor);

		CompletableFuture<Void> descFuture = skuInfoFuture.thenAcceptAsync(e -> {
			//4、spu的介绍
			SpuInfoDescEntity spuInfoDesc = spuInfoDescService.getById(e.getSkuId());
			retVo.setDesc(spuInfoDesc);
		}, executor);
		CompletableFuture<Void> attrGroupFuture = skuInfoFuture.thenAcceptAsync(e -> {
			//5、spu规格参数信息
			List<SpuItemAttrGroupVo> attrsGroup = attrGroupService.getAttrGroupWithAttrsBySpuIdByCarter(e.getSpuId(), e.getCategoryId());
			retVo.setGroupAttrs(attrsGroup);
		}, executor);

		CompletableFuture<Void> imageFuture = CompletableFuture.runAsync(() -> {
			//2、sku图片信息sku_img
			List<SkuImagesEntity> images = skuImagesService.getImagesBySkuId(skuId);
			retVo.setImages(images);
		}, executor);

		CompletableFuture<Void> secondKilFuture = CompletableFuture.runAsync(() -> {
			//查询当前sku是否有秒杀优惠
			R skuSecondKillInfo = secondKillFeignService.getSkuSecondKillInfo(skuId);
			SecondKillInfoVo data = skuSecondKillInfo.getData(new TypeReference<SecondKillInfoVo>() {
			});
			retVo.setSecondKillInfo(data);
		}, executor);

		//等到所有任务完成
		CompletableFuture.allOf(saleAttrsFuture, descFuture, attrGroupFuture, imageFuture, secondKilFuture).get();
		return retVo;

	}

	@Override
	public List<SkuInfoEntity> getSkusBySpuIdByCarter(Long spuId) {

		List<SkuInfoEntity> skuList = this.list(new QueryWrapper<SkuInfoEntity>().eq("spu_id", spuId));
		return skuList;
	}

	@Override
	public SkuInfoEntity getPriceBySkuId(Long skuId) {
		SkuInfoDao skuInfoDao = this.baseMapper;
		SkuInfoEntity result = skuInfoDao.selectById(skuId);
		return result;
	}

	private SkuItemVo itemSync(Long skuId) {
		SkuItemVo skuItemVo = new SkuItemVo();

		//1、sku详细信息sku_info
		CompletableFuture<SkuInfoEntity> infoFuture = CompletableFuture.supplyAsync(() -> {
			SkuInfoEntity skuInfo = getById(skuId);
			skuItemVo.setInfo(skuInfo);
			return skuInfo;
		}, executor);

		CompletableFuture<Void> saleAttrFuture = infoFuture.thenAcceptAsync(skuInfo -> {
			//3、spu销售属性组合
			List<SkuItemSaleAttrVo> saleAttr = skuSaleAttrValueService.getSaleAttrBySpuId(skuInfo.getSpuId());
			skuItemVo.setSaleAttr(saleAttr);
			System.out.println(saleAttr);
		}, executor);
		CompletableFuture<Void> descFuture = infoFuture.thenAcceptAsync(skuInfo -> {
			//4、spu的介绍
			SpuInfoDescEntity spuInfoDesc = spuInfoDescService.getById(skuInfo.getSpuId());
			skuItemVo.setDesc(spuInfoDesc);
		}, executor);

		CompletableFuture<Void> attrGroupFuture = infoFuture.thenAcceptAsync(skuInfo -> {
			//5、spu规格参数信息
			Long categoryId = skuInfo.getCategoryId();
			List<SpuItemAttrGroupVo> groupAttrs = attrGroupService.getAttrGroupWithAttrsBySpuId(skuInfo.getSpuId(), categoryId);
			System.out.println(groupAttrs);
			skuItemVo.setGroupAttrs(groupAttrs);
		}, executor);

		CompletableFuture<Void> imageFuture = CompletableFuture.runAsync(() -> {
			List<SkuImagesEntity> images = skuImagesService.getImagesBySkuId(skuId);
			skuItemVo.setImages(images);
		}, executor);

		//等待所有任务完成
		try {
			CompletableFuture.allOf(saleAttrFuture, descFuture, attrGroupFuture, imageFuture).get();
		} catch (InterruptedException e) {
			log.error("查询商品详情异步编排错误: ");
			log.error(e.getMessage());
		} catch (ExecutionException e) {
			log.error(e.getMessage());
		}
		return skuItemVo;
	}
}