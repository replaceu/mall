package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.product.dao.SkuInfoDao;
import com.gulimall.product.entity.SkuInfoEntity;
import com.gulimall.product.service.SkuInfoService;
import com.gulimall.product.vo.SkuPageVo;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.service.utils.QueryPage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {


    @Override
    public PageUtils queryPageOnCondition(SkuPageVo params) {

        LambdaQueryWrapper<SkuInfoEntity> wrapper = new LambdaQueryWrapper<>();

        // 封装 区间
        if (params.getBrandId() !=null && params.getBrandId() > 0 ){
            wrapper.eq(SkuInfoEntity::getBrandId , params.getBrandId() ) ;
        }
        if (params.getCategoryId() != null && params.getCategoryId() > 0 ){
            wrapper.eq(SkuInfoEntity::getCategoryId , params.getCategoryId() ) ;
        }

        if (!StringUtils.isEmpty(params.getKey())) {
            wrapper.and(w->{
                w.eq(SkuInfoEntity::getSkuId, params.getKey() )
                        .or()
                        .like(SkuInfoEntity::getSkuName ,params.getKey()) ;
            }) ;
        }
            if (params.getMax() != null &&  params.getMax().compareTo(BigDecimal.ZERO ) > 0){
                wrapper.le(SkuInfoEntity::getPrice , params.getMax() ) ;
            }
        if (params.getMin() != null  ){
               wrapper.ge(SkuInfoEntity::getPrice , params.getMin() ) ;
        }



        IPage<SkuInfoEntity> spuInfoEntityIPage = baseMapper.selectPage(new QueryPage<SkuInfoEntity>().getPage(params), wrapper);
        return new PageUtils(spuInfoEntityIPage) ;

    }

    @Transactional
    @Override
    public void saveSkuInfo(SkuInfoEntity skuInfoEntity) {
        baseMapper.insert(skuInfoEntity) ;
    }



}