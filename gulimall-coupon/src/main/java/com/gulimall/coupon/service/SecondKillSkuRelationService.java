package com.gulimall.coupon.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.coupon.entity.SecondKillSkuRelationEntity;
import com.gulimall.service.utils.PageUtils;

/**
 * 秒杀活动商品关联
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-09 09:23:34
 */
public interface SecondKillSkuRelationService extends IService<SecondKillSkuRelationEntity> {

	PageUtils queryPage(Map<String, Object> params);
}
