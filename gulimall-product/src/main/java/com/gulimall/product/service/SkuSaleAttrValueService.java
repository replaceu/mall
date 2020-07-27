package com.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.product.entity.SkuSaleAttrValueEntity;
import com.gulimall.service.utils.PageUtils;

import java.util.Map;

/**
 * sku销售属性&值
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-10 11:26:28
 */
public interface SkuSaleAttrValueService extends IService<SkuSaleAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

