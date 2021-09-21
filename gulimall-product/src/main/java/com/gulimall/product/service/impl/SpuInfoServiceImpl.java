package com.gulimall.product.service.impl;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.common.constant.ProductConstant;
import com.gulimall.common.exception.BusinessException;
import com.gulimall.common.exception.CouponErrorCode;
import com.gulimall.common.to.SkuHasStockTo;
import com.gulimall.common.to.SkuReductionTo;
import com.gulimall.common.to.SpuBoundTo;
import com.gulimall.common.to.es.Attrs;
import com.gulimall.common.to.es.SkuEsModel;
import com.gulimall.common.utils.CommonResult;
import com.gulimall.product.convert.AttrConvert;
import com.gulimall.product.convert.SkuConvert;
import com.gulimall.product.convert.SpuConvert;
import com.gulimall.product.dao.SpuInfoDao;
import com.gulimall.product.entity.*;
import com.gulimall.product.feign.CouponFeignService;
import com.gulimall.product.feign.SearchFeignService;
import com.gulimall.product.feign.WareFeignService;
import com.gulimall.product.service.*;
import com.gulimall.product.vo.*;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.service.utils.QueryPage;

import lombok.extern.slf4j.Slf4j;

@Service("spuInfoService")
@Slf4j
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

	@Autowired
	SpuInfoDescService		spuInfoDescService;
	@Autowired
	SpuImagesService		spuImagesService;
	@Autowired
	AttrService				attrService;
	@Autowired
	SkuInfoService			skuInfoService;
	@Autowired
	SkuImagesService		skuImageService;
	@Autowired
	ProductAttrValueService	productAttrValueService;
	@Autowired
	SkuSaleAttrValueService	skuSaleAttrValueService;
	@Autowired
	CouponFeignService		couponFeignService;
	@Autowired
	BrandService			brandService;
	@Autowired
	CategoryService			categoryService;
	@Autowired
	WareFeignService		wareFeignService;
	@Autowired
	SearchFeignService		searchFeignService;

	/**
	 * TODO 如果失败，全局事务
	 * @param spuSaveVo
	 */
	@Transactional
	@Override
	public void saveInfo(SpuSaveVo spuSaveVo) {
		//1 保存spu 基本信息spu_info
		SpuInfoEntity spuInfoEntity = SpuConvert.INSTANCE.vo2entity(spuSaveVo);
		baseMapper.insert(spuInfoEntity);
		//2 保存spu的描述图片 spu_info_desc
		List<String> decript = spuSaveVo.getDecript();
		SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
		spuInfoDescEntity.setSpuId(spuInfoEntity.getId());
		String desc = String.join(",", decript);
		spuInfoDescEntity.setDecript(desc);
		spuInfoDescService.save(spuInfoDescEntity);
		//3 保存sup的图片集 spu_images
		List<String> images = spuSaveVo.getImages();
		spuImagesService.saveImages(spuInfoEntity.getId(), images);
		//4 保存spu的规格参数 pms_product_attr_value
		List<SpuSaveBaseAttrs> baseAttrs = spuSaveVo.getBaseAttrs();

		List<ProductAttrValueEntity> productAttrValueEntities = baseAttrs.stream().map(baseAttr -> {
			ProductAttrValueEntity productAttrValueEntity = new ProductAttrValueEntity();

			productAttrValueEntity.setSpuId(spuInfoEntity.getId());
			productAttrValueEntity.setAttrId(baseAttr.getAttrId());

			AttrEntity attrInfo = attrService.getById(baseAttr.getAttrId());

			productAttrValueEntity.setAttrName(attrInfo.getAttrName());

			productAttrValueEntity.setQuickShow(baseAttr.getShowDesc());
			productAttrValueEntity.setAttrValue(baseAttr.getAttrValues());
			return productAttrValueEntity;
		}).collect(Collectors.toList());
		//不用自动生成的方法,后面可以配置缓存
		productAttrValueService.saveAttrValueInfo(productAttrValueEntities);
		//4、保存spu的规格参数;pms_product_attr_value
		//5.保存spu 积分信息
		SpuSaveBounds bounds = spuSaveVo.getBounds();
		SpuBoundTo spuBoundTo = new SpuBoundTo();
		spuBoundTo.setSpuId(spuInfoDescEntity.getSpuId());
		spuBoundTo.setBuyBounds(bounds.getBuyBounds());
		spuBoundTo.setGrowBounds(bounds.getGrowBounds());
		CommonResult saveSpuBoundsResult = couponFeignService.saveSpuBounds(spuBoundTo);

		if (saveSpuBoundsResult.getCode() != 0) {
			log.error("保存spu积分信息失败");
			throw new BusinessException(CouponErrorCode.SAVE_SPU_BOUND_FAIL);
		}
		//5 spu对应的sku信息
		//5.1基本信息sku_info
		List<SpuSaveSku> skus = spuSaveVo.getSkus();
		if (skus != null && skus.size() > 0) {
			skus.forEach(sku -> {
				String defaultImg = "";
				List<SpuSaveImage> imageList = sku.getImages();
				for (SpuSaveImage img : imageList) {
					if (img.getDefaultImg() == 1) {
						defaultImg = img.getImgUrl();
						break;
					}
				}
				//private String skuName;
				//private BigDecimal price;
				//private String skuTitle;
				//private String skuSubtitle;
				SkuInfoEntity skuInfoEntity = SkuConvert.INSTANCE.vo2entity(sku);
				//复制不一样的地方
				skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
				skuInfoEntity.setCategoryId(spuInfoEntity.getCategoryId());
				skuInfoEntity.setSaleCount(0L);
				skuInfoEntity.setSpuId(spuInfoEntity.getId());
				skuInfoEntity.setSkuDefaultImg(defaultImg);
				//TODO 如果是采用分布式主键生成 则是一个批量保存
				skuInfoService.saveSkuInfo(skuInfoEntity);

				Long skuId = skuInfoEntity.getSkuId();
				//5.2  图片信息 sku_info
				//没有图片路径的无需保存
				List<SkuImagesEntity> imagesEntities = imageList.stream().filter(img -> !StringUtils.isEmpty(img.getImgUrl())).map(img -> {
					SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
					skuImagesEntity.setSkuId(skuId);
					skuImagesEntity.setImgUrl(img.getImgUrl());
					skuImagesEntity.setDefaultImg(img.getDefaultImg());
					return skuImagesEntity;
				}).collect(Collectors.toList());
				skuImageService.saveBatch(imagesEntities);
				//5.3 sku的销售属性 sku_sale_attr_value
				List<SpuSaveAttr> attr = sku.getAttr();
				List<SkuSaleAttrValueEntity> skuSaleAttrValueEntities = attr.stream().map(item -> {
					SkuSaleAttrValueEntity attrValueEntity = SkuConvert.INSTANCE.vo2entity(item);
					attrValueEntity.setSkuId(skuId);
					return attrValueEntity;
				}).collect(Collectors.toList());
				skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntities);
				//5.4 优惠信息
				//会员价、满减、打折 、积分
				SkuReductionTo skuReductionTo = new SkuReductionTo();
				skuReductionTo = SkuConvert.INSTANCE.vo2to(sku);

				skuReductionTo.setSkuId(skuId);
				if (skuReductionTo.getFullCount() > 0 || skuReductionTo.getFullPrice().compareTo(new BigDecimal("0")) == 1) {
					CommonResult skuReductionResult = couponFeignService.saveSkuReduction(skuReductionTo);
					if (skuReductionResult.getCode() != 0) {
						log.error("保存sku优惠信息失败");
						throw new BusinessException(CouponErrorCode.SAVE_KEU_RELATION_FAIL);
					}
				}
			});
		}
	}

	@Override
	public PageUtils queryPageOnCondition(SpuPageVo params) {
		LambdaQueryWrapper<SpuInfoEntity> wrapper = new LambdaQueryWrapper<>();

		if (params.getStatus() != null) {
			wrapper.eq(SpuInfoEntity::getPublishStatus, params.getStatus());
		}
		if (!StringUtils.isEmpty(params.getKey())) {
			wrapper.and(w -> {
				w.eq(SpuInfoEntity::getId, params.getKey()).or().like(SpuInfoEntity::getSpuName, params.getKey());
			});
		}

		if (params.getBrandId() != null && params.getBrandId() > 0) {
			wrapper.eq(SpuInfoEntity::getBrandId, params.getBrandId());
		}
		if (params.getCategoryId() != null && params.getCategoryId() > 0) {
			wrapper.eq(SpuInfoEntity::getCategoryId, params.getCategoryId());
		}
		IPage<SpuInfoEntity> spuInfoEntityIPage = baseMapper.selectPage(new QueryPage<SpuInfoEntity>().getPage(params), wrapper);
		return new PageUtils(spuInfoEntityIPage);
	}

	@Transactional
	@Override
	public void up(Long spuId) {
		//组装数据 并发送给es
		List<SkuInfoEntity> skuInfoEntities = skuInfoService.getSkusBySpuId(spuId);

		//3、
		List<ProductAttrValueEntity> attrValueEntities = productAttrValueService.baseAttrListForSpuId(spuId);

		List<Long> attrIds = attrValueEntities.stream().map(ProductAttrValueEntity::getAttrId).collect(Collectors.toList());
		/**
		 * 可以用来检索的 id
		 */
		List<Long> searchAttrIds = attrService.getSearchAttrIds(attrIds);

		//遍历得到可以存放的数据
		HashSet<Long> set = new HashSet<>(searchAttrIds);
		List<Attrs> searchAttrList = attrValueEntities.stream().filter(attrValue -> {
			return set.contains(attrValue.getAttrId());
		}).map(e -> {
			Attrs attrs = AttrConvert.INSTANCE.entity2model(e);
			return attrs;
		}).collect(Collectors.toList());
		Map<Long, Boolean> hasStock = null;
		try {
			List<Long> skuIds = skuInfoEntities.stream().map(SkuInfoEntity::getSkuId).collect(Collectors.toList());
			List<SkuHasStockTo> stockTos = wareFeignService.hasStock(skuIds).getData();
			hasStock = stockTos.stream().collect(Collectors.toMap(SkuHasStockTo::getSkuId, SkuHasStockTo::getHasStock));
		} catch (Exception e) {
			log.error("远程库存服务异常:  {}", e);
		}
		//参数对拷
		Map<Long, Boolean> finalHasStock = hasStock;
		List<SkuEsModel> skuEsModelList = skuInfoEntities.stream().map(skuInfoEntity -> {
			SkuEsModel skuEsModel = SkuConvert.INSTANCE.entity2model(skuInfoEntity);
			//1、价格,图片hasStock  在convert中配置
			//TODO 库存系统是否有库存
			skuEsModel.setHotScore(0L);

			if (finalHasStock != null) {
				skuEsModel.setHasStock(finalHasStock.get(skuInfoEntity.getSkuId()));
			} else {
				skuEsModel.setHasStock(true);
			}

			//2、查询品牌 及分类
			BrandEntity brandEntity = brandService.getById(skuInfoEntity.getBrandId());
			skuEsModel.setBrandName(brandEntity.getName());
			skuEsModel.setBrandImg(brandEntity.getLogo());
			skuEsModel.setBrandId(brandEntity.getBrandId());

			CategoryEntity categoryEntity = categoryService.getById(skuInfoEntity.getCategoryId());
			skuEsModel.setCategoryName(categoryEntity.getName());

			//3、规格属性  sku中  相同属性
			skuEsModel.setAttrs(searchAttrList);
			return skuEsModel;
		}).collect(Collectors.toList());
		//4 es 保存
		CommonResult commonResult = searchFeignService.productStatusUp(skuEsModelList);
		Integer code = commonResult.getCode();
		if (code == 0) {
			//log.info("商品es保存成功");
			//更新 数据库信息
			SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
			spuInfoEntity.setId(spuId);
			spuInfoEntity.setPublishStatus(ProductConstant.SPU_STATUS_UP);
			baseMapper.updateById(spuInfoEntity);
		} else {
			log.error("商品es保存失败");
		}
		//TODO 重复调用  接口幂等性

		/**
		 * feign 请求流程
		 * 1、构造请求数据,将对象转为json
		 * 2、发送请求
		 *  SynchronousMethodHandler/executeAndDecode
		 * 3、有重试
		 * Retryer
		 */

	}

	@Override
	public void upProduct(Long spuId) {

		//1.查出当前spuId对应的sku信息，品牌的名字
		List<SkuInfoEntity> skus = skuInfoService.getSkusBySpuIdByCarter(spuId);

		//todo:查询当前sku的所有可以用来检索的规格属性(保存在product_attr_value表中)
		List<ProductAttrValueEntity> baseAttrs = productAttrValueService.baseAttrListForSpuId(spuId);

		//将所有的id映射成List
		List<Long> attrIds = baseAttrs.stream().map(attr -> {
			return attr.getAttrId();
		}).collect(Collectors.toList());

		//可以用作检索的attrIds
		List<Long> searchAttrIds = attrService.selectSearchAttrIds(attrIds);
		Set<Long> attrIdSet = new HashSet<>();

		List<Attrs> attrs = new ArrayList<>();
		//过滤出可以用作检索的attrIds的对象，再通过对拷成为用作es存储的对象
		List<Attrs> esModelAttrList = baseAttrs.stream().filter(e -> {
			return attrIdSet.contains(e.getAttrId());
		}).map(e -> {
			Attrs esAttrs = new Attrs();
			BeanUtils.copyProperties(e, esAttrs);
			return esAttrs;
		}).collect(Collectors.toList());

		//todo:库存系统查询是否有库存,为防止网络波动try-catch
		Map<Long, Boolean> stockMap = null;
		try {
			CommonResult<List<SkuHasStockTo>> skuHasStock = wareFeignService.hasStock(skus.stream().map(e -> e.getSkuId()).collect(Collectors.toList()));
			stockMap = skuHasStock.getData().stream().collect(Collectors.toMap(SkuHasStockTo::getSkuId, SkuHasStockTo::getHasStock));
		} catch (Exception e) {
			log.error("库存查询异常：原因{}", e);
		}

		//2.封装每个sku的信息
		Map<Long, Boolean> finalStockMap = stockMap;
		List<SkuEsModel> upProducts = skus.stream().map(skuInfoEntity -> {
			SkuEsModel esModel = new SkuEsModel();
			BeanUtils.copyProperties(skuInfoEntity, esModel);
			//对拷之后，skuPrice,skuImg,hasStock,hotScore,brandName,catalogName,List<Attrs> attrs需要单独处理
			esModel.setSkuPrice(skuInfoEntity.getPrice());
			esModel.setSkuImg(skuInfoEntity.getSkuDefaultImg());
			esModel.setHasStock(finalStockMap == null ? true : finalStockMap.get(skuInfoEntity.getSkuId()));
			//todo:热度评分默认是0
			//todo:查询品牌和分类的名字信息
			BrandEntity brand = brandService.getById(esModel.getBrandId());
			esModel.setBrandName(brand.getName());
			esModel.setBrandImg(brand.getLogo());
			CategoryEntity category = categoryService.getById(esModel.getCategoryId());
			esModel.setCategoryName(category.getName());

			//设置检索属性
			esModel.setAttrs(esModelAttrList);

			return esModel;
		}).collect(Collectors.toList());
		//todo:发给es进行保存，SearchService进行检索
		CommonResult result = searchFeignService.productStatusUp(upProducts);
		if (result.getCode() == 0) {
			//todo:远程调用成功之后需要修改商品上架状态
			SpuInfoEntity upSpuInfo = new SpuInfoEntity();
			upSpuInfo.setId(spuId);
			upSpuInfo.setPublishStatus(ProductConstant.SPU_STATUS_UP);
			upSpuInfo.setUpdateTime(new Date());
			this.baseMapper.updateById(upSpuInfo);
		} else {
			//远程调用失败
			//todo:接口幂等性，重试机制
		}

	}
}