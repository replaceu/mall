package com.gulimall.product.convert;

import com.gulimall.common.product.dto.BrandDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.gulimall.product.entity.BrandEntity ;
/**
 * @author aqiang9  2020-07-29
 */
@Mapper
public interface BrandConvert {
    BrandConvert INSTANCE = Mappers.getMapper(BrandConvert.class) ;
    BrandDto entity2dto(BrandEntity entity) ;
    BrandEntity dto2entity(BrandDto dto) ;
}
