package com.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.ware.entity.WareSkuEntity;
import com.gulimall.ware.vo.WareSkuPageVo;

/**
 * 商品库存
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-09 10:07:53
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(WareSkuPageVo params);

    void addStock(Long skuId, Long wareId, Integer skuNum);
}

