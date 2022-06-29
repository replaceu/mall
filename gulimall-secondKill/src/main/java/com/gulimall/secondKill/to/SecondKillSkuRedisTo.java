package com.gulimall.secondKill.to;

import java.util.Date;
import java.util.List;

import com.gulimall.secondKill.vo.SecondKillSkuVo;
import com.gulimall.secondKill.vo.SkuInfoVo;

import lombok.Data;

@Data
public class SecondKillSkuRedisTo {

	private Long id;

	/**
	 * 场次名称
	 */
	private String name;

	/**
	 * 每日开始时间
	 */
	private Date startTime;

	/**
	 * 每日结束时间
	 */
	private Date	endTime;
	/**
	 * 启用状态
	 */
	private Integer	status;
	/**
	 * 创建时间
	 */
	private Date	createTime;

	private List<SecondKillSkuVo> relationEntities;

	private SkuInfoVo skuInfo;

	/**
	 * 随机码
	 */
	private String randomCode;

	/**
	 * 活动场次id
	 */
	private Long promotionSessionId;

}
