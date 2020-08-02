package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.common.convert.ProductConvert;
import com.gulimall.common.exception.BusinessException;
import com.gulimall.common.exception.CouponErrorCode;
import com.gulimall.common.to.SkuReductionTo;
import com.gulimall.common.to.SpuBoundTo;
import com.gulimall.common.utils.CommonResult;
import com.gulimall.product.convert.SkuConvert;
import com.gulimall.product.convert.SpuConvert;
import com.gulimall.product.dao.SpuInfoDao;
import com.gulimall.product.entity.*;
import com.gulimall.product.feign.CouponFeignService;
import com.gulimall.product.service.*;
import com.gulimall.product.vo.*;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.service.utils.Query;
import com.gulimall.service.utils.QueryPage;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("spuInfoService")
@Slf4j
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    SpuInfoDescService spuInfoDescService;

    @Autowired
    SpuImagesService spuImagesService;
    @Autowired
    AttrService attrService;
    @Autowired
    SkuInfoService skuInfoService;

    @Autowired
    SkuImagesService skuImageService;

    @Autowired
    ProductAttrValueService productAttrValueService;
    @Autowired
    SkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    CouponFeignService couponFeignService;


    /**
     * TODO 如果失败  全局事务
     * @param spuSaveVo
     */
    @Transactional
    @Override
    public void saveInfo(SpuSaveVo spuSaveVo) {
        // 1、保存spu 基本信息  spu_info
        SpuInfoEntity spuInfoEntity = SpuConvert.INSTANCE.vo2entity(spuSaveVo);
        baseMapper.insert(spuInfoEntity);
        // 2 保存spu的描述图片 spu_info_desc
        List<String> decript = spuSaveVo.getDecript();
        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
        spuInfoDescEntity.setSpuId(spuInfoEntity.getId());
        String desc = String.join(",", decript);
        spuInfoDescEntity.setDecript(desc);
        spuInfoDescService.save(spuInfoDescEntity);

//      3、保存sup的图片集 spu_images
        List<String> images = spuSaveVo.getImages();
        spuImagesService.saveImages(spuInfoEntity.getId(), images);
//        4、保存spu的规格参数 pms_product_attr_value
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
        // 不用自动生成的方法 ，  后面可以配置缓存
        productAttrValueService.saveAttrValueInfo(productAttrValueEntities);

        //4、保存spu的规格参数;pms_product_attr_value


        // 5.0 、保存spu 积分信息

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
        // 5 spu 对应的sku信息
//        5.1  基本信息 sku_info
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
                //    private String skuName;
                //    private BigDecimal price;
                //    private String skuTitle;
                //    private String skuSubtitle;
                SkuInfoEntity skuInfoEntity = SkuConvert.INSTANCE.vo2entity(sku);
                // 复制不一样的地方
                skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
                ;
                skuInfoEntity.setCategoryId(spuInfoEntity.getCategoryId());
                skuInfoEntity.setSaleCount(0L);
                skuInfoEntity.setSpuId(spuInfoEntity.getId());
                skuInfoEntity.setSkuDefaultImg(defaultImg);

                // TODO 如果是采用分布式主键生成 则是一个批量保存
                skuInfoService.saveSkuInfo(skuInfoEntity);

                Long skuId = skuInfoEntity.getSkuId();

//        5.2  图片信息 sku_info
                // 没有图片路径的无需保存
                List<SkuImagesEntity> imagesEntities = imageList.stream().filter(img ->
                        !StringUtils.isEmpty(img.getImgUrl())
                ).map(img -> {
                    SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                    skuImagesEntity.setSkuId(skuId);
                    skuImagesEntity.setImgUrl(img.getImgUrl());
                    skuImagesEntity.setDefaultImg(img.getDefaultImg());
                    return skuImagesEntity;
                }).collect(Collectors.toList());
                skuImageService.saveBatch(imagesEntities);


//        5.3 sku的销售属性 sku_sale_attr_value
                List<SpuSaveAttr> attr = sku.getAttr();
                List<SkuSaleAttrValueEntity> skuSaleAttrValueEntities = attr.stream().map(item -> {
                    SkuSaleAttrValueEntity attrValueEntity = SkuConvert.INSTANCE.vo2entity(item);
                    attrValueEntity.setSkuId(skuId);
                    return attrValueEntity;
                }).collect(Collectors.toList());
                skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntities);

//        5.4 优惠信息
//              会员价、满减、打折 、积分
                SkuReductionTo skuReductionTo = new SkuReductionTo();
                skuReductionTo = SkuConvert.INSTANCE.vo2to(sku);

                skuReductionTo.setSkuId(skuId);

                if (skuReductionTo.getFullCount() > 0 || skuReductionTo.getFullPrice().compareTo(new BigDecimal("0")) == 1) {
                    CommonResult skuReductionResult = couponFeignService.saveSkuReduction(skuReductionTo);

                    if (skuReductionResult.getCode() != 1) {
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

        if (params.getStatus()!=null) {
          wrapper.eq(SpuInfoEntity::getPublishStatus , params.getStatus() );
        }
        if (!StringUtils.isEmpty(params.getKey())) {
            wrapper.and(w->{
                w.eq(SpuInfoEntity::getId, params.getKey() )
                        .or()
                        .like(SpuInfoEntity::getSpuName ,params.getKey()) ;
            }) ;
        }

        if (params.getBrandId() !=null && params.getBrandId() > 0 ){
            wrapper.eq(SpuInfoEntity::getBrandId , params.getBrandId() ) ;
        }
        if (params.getCategoryId() != null  && params.getCategoryId() > 0 ){
            wrapper.eq(SpuInfoEntity::getCategoryId , params.getCategoryId() ) ;
        }
        IPage<SpuInfoEntity> spuInfoEntityIPage = baseMapper.selectPage(new QueryPage<SpuInfoEntity>().getPage(params), wrapper);
        return new PageUtils(spuInfoEntityIPage) ;
    }

}