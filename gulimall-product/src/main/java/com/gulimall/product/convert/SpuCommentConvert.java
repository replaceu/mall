package com.gulimall.product.convert;

import com.gulimall.common.product.dto.SpuCommentDto;
import com.gulimall.product.entity.SpuCommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author aqiang9  2020-07-29
 */
@Mapper
public interface SpuCommentConvert {
    SpuCommentConvert INSTANCE = Mappers.getMapper(SpuCommentConvert.class);

    SpuCommentDto entity2dto(SpuCommentEntity entity);

    SpuCommentEntity dto2entity(SpuCommentDto dto);
}
