package com.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.common.vo.PageVo;
import com.gulimall.product.entity.AttrGroupEntity;
import com.gulimall.service.utils.PageUtils;

import java.util.Map;

/**
 * 属性分组
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-10 11:26:28
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage(PageVo pageParam, Long categoryId);

}

