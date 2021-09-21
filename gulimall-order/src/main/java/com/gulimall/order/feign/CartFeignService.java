package com.gulimall.order.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.gulimall.order.vo.OrderItemVo;

@FeignClient("gulimall-cart")
public interface CartFeignService {
	@GetMapping("/currentUserItems")
	List<OrderItemVo> getCurrentUserCartItems();
}
