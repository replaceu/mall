package com.gulimall.coupon.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.coupon.entity.SecondKillSessionEntity;
import com.gulimall.service.utils.PageUtils;

/**
 * 秒杀活动场次
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-09 09:23:34
 */
public interface SecondKillSessionService extends IService<SecondKillSessionEntity> {

	PageUtils queryPage(Map<String, Object> params);

	List<SecondKillSessionEntity> getLasts3DaySession();
}
