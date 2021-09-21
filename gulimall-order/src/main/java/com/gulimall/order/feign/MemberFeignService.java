package com.gulimall.order.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gulimall.order.vo.MemberAddressVo;

/**
 * @author Carter
 * @date 2021-08-29 04:26
 * @description:
 * @version:
 */
@FeignClient("gulimall-member")
public interface MemberFeignService {
	//通过用户的id得到用户地址列表
	@GetMapping("/{memberId}/addresses")
	List<MemberAddressVo> getAddress(@PathVariable("memberId") Long memberId);

}
