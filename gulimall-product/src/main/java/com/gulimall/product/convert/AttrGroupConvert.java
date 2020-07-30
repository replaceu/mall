package com.gulimall.product.convert;


import com.gulimall.common.product.dto.AttrGroupDto;
import com.gulimall.product.entity.AttrGroupEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author aqiang9  2020-07-30
 */
@Mapper
public interface AttrGroupConvert {
    AttrGroupConvert INSTANCE = Mappers.getMapper(AttrGroupConvert.class) ;
    AttrGroupEntity dto2entity(AttrGroupDto dto);
    AttrGroupDto entity2dto(AttrGroupEntity entity);

}
