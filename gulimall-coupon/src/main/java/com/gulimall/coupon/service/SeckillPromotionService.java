package com.gulimall.coupon.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.coupon.entity.SeckillPromotionEntity;
import com.gulimall.service.utils.PageUtils;

public interface SeckillPromotionService extends IService<SeckillPromotionEntity> {

	PageUtils queryPage(Map<String, Object> params);
}
