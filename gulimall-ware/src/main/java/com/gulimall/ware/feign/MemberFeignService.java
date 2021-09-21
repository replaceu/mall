package com.gulimall.ware.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gulimall.common.utils.R;

@FeignClient("gulimall-member")
public interface MemberFeignService {

	/**
	 * 信息
	 */
	@RequestMapping("member/memberreceiveaddress/info/{id}")
	R info(@PathVariable("id") Long id);
}
