package com.gulimall.secondKill.feign;

import org.springframework.web.bind.annotation.GetMapping;

import com.gulimall.common.utils.R;

public interface CouponFeignService {

	@GetMapping("/coupon/secondKillSession/lasts3DaySession")
	R getLasts3DaySession();

}
