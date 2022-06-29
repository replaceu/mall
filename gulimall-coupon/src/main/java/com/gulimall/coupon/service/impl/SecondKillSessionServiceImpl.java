package com.gulimall.coupon.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.coupon.dao.SecondKillSessionDao;
import com.gulimall.coupon.entity.SecondKillSessionEntity;
import com.gulimall.coupon.entity.SecondKillSkuRelationEntity;
import com.gulimall.coupon.service.SecondKillSessionService;
import com.gulimall.coupon.service.SecondKillSkuRelationService;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.service.utils.Query;

@Service("secondKillSessionService")
public class SecondKillSessionServiceImpl extends ServiceImpl<SecondKillSessionDao, SecondKillSessionEntity> implements SecondKillSessionService {

	@Autowired
	SecondKillSkuRelationService secondKillSkuRelationService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<SecondKillSessionEntity> page = this.page(new Query<SecondKillSessionEntity>().getPage(params), new QueryWrapper<SecondKillSessionEntity>());

		return new PageUtils(page);
	}

	@Override
	public List<SecondKillSessionEntity> getLasts3DaySession() {
		SecondKillSessionDao secondKillSessionDao = this.baseMapper;
		List<SecondKillSessionEntity> secondKillSessionEntities = secondKillSessionDao.selectList(new QueryWrapper<SecondKillSessionEntity>().between("start_time", startTime(), endTime()));
		if (secondKillSessionEntities != null && secondKillSessionEntities.size() > 0) {
			List<SecondKillSessionEntity> killSessionEntities = secondKillSessionEntities.stream().map(e -> {
				Long sessionId = e.getId();
				List<SecondKillSkuRelationEntity> skuRelationEntityList = secondKillSkuRelationService.list(new QueryWrapper<SecondKillSkuRelationEntity>().eq("promotion_session_id", sessionId));
				e.setSecondKillSkuRelationEntities(skuRelationEntityList);

				return e;
			}).collect(Collectors.toList());

			return killSessionEntities;
		}
		return null;
	}

	/**
	 * 起始时间
	 *
	 * @return
	 */
	private String startTime() {
		LocalDate now = LocalDate.now();
		LocalTime time = LocalTime.MIN;
		LocalDateTime start = LocalDateTime.of(now, time);

		String format = start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		return format;
	}

	/**
	 * 结束时间
	 *
	 * @return
	 */
	private String endTime() {
		LocalDate now = LocalDate.now();
		LocalDate localDate = now.plusDays(2);
		LocalTime time = LocalTime.MIN;
		LocalDateTime end = LocalDateTime.of(localDate, time);
		String format = end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		return format;
	}

}