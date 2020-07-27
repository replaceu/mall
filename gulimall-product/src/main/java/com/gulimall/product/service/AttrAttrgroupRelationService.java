package com.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.gulimall.service.utils.PageUtils;

import java.util.Map;

/**
 * 属性&属性分组关联
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-10 11:26:28
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

