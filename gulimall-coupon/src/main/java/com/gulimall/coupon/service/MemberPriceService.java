package com.gulimall.coupon.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.coupon.entity.MemberPriceEntity;
import com.gulimall.service.utils.PageUtils;

/**
 * 商品会员价格
 *
 * @author Carter
 * @email 2903780002@qq.com
 * @date 2020-06-09 09:23:34
 */
public interface MemberPriceService extends IService<MemberPriceEntity> {

	PageUtils queryPage(Map<String, Object> params);
}
