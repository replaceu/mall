package com.gulimall.product.convert;

import com.gulimall.product.dto.CategoryDto;
import com.gulimall.product.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author aqiang9  2020-07-28
 */
@Mapper
public interface CategoryConvert {
    CategoryConvert INSTANCE = Mappers.getMapper(CategoryConvert.class) ;
    CategoryDto entity2dto(CategoryEntity entity);
    CategoryEntity dto2entity(CategoryDto dto) ;
}
