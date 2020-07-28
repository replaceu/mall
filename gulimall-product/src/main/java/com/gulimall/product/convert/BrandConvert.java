package com.gulimall.product.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * @author aqiang9  2020-07-28
 */
@Mapper
public interface BrandConvert {
    BrandConvert INSTANCE = Mappers.getMapper(BrandConvert.class) ;

}
