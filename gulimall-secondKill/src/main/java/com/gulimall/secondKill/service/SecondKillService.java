package com.gulimall.secondKill.service;

import java.util.List;

import com.gulimall.secondKill.to.SecondKillSkuRedisTo;

public interface SecondKillService {

	void uploadSecondKillSkuLatest3Days();

	List<SecondKillSkuRedisTo> getCurrentSecondKillSkus();

	SecondKillSkuRedisTo getSkuSecondKillInfo(Long skuId);

	String secondKill(String killId, String key, Integer num);
}
