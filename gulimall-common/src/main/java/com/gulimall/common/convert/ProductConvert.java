package com.gulimall.common.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * gulimall-product 需要用到的转换类
 * @author aqiang9  2020-08-02
 */
@Mapper
public interface ProductConvert {
    ProductConvert INSTANCE = Mappers.getMapper(ProductConvert.class) ;

}
