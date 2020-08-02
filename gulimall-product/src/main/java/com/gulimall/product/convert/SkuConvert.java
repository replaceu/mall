package com.gulimall.product.convert;

import com.gulimall.common.to.SkuReductionTo;
import com.gulimall.product.entity.SkuImagesEntity;
import com.gulimall.product.entity.SkuInfoEntity;
import com.gulimall.product.entity.SkuSaleAttrValueEntity;
import com.gulimall.product.vo.SpuSaveAttr;
import com.gulimall.product.vo.SpuSaveImage;
import com.gulimall.product.vo.SpuSaveSku;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author aqiang9  2020-08-02
 */
@Mapper
public interface SkuConvert {
    SkuConvert INSTANCE = Mappers.getMapper(SkuConvert.class) ;

    SkuInfoEntity vo2entity(SpuSaveSku sku);

    SkuImagesEntity vo2entity(SpuSaveImage img);

    SkuSaleAttrValueEntity vo2entity(SpuSaveAttr item);

    SkuReductionTo vo2to(SpuSaveSku sku);
}
