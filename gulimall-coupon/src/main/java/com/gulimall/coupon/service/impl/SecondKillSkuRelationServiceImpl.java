package com.gulimall.coupon.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.coupon.dao.SecondKillSkuRelationDao;
import com.gulimall.coupon.entity.SecondKillSkuRelationEntity;
import com.gulimall.coupon.service.SecondKillSkuRelationService;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.service.utils.Query;

@Service("secondKillSkuRelationService")
public class SecondKillSkuRelationServiceImpl extends ServiceImpl<SecondKillSkuRelationDao, SecondKillSkuRelationEntity> implements SecondKillSkuRelationService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<SecondKillSkuRelationEntity> page = this.page(new Query<SecondKillSkuRelationEntity>().getPage(params), new QueryWrapper<SecondKillSkuRelationEntity>());

		return new PageUtils(page);
	}

}