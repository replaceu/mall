package com.gulimall.integral.feign;

import com.gulimall.common.utils.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface OrderFeignService {

    @GetMapping("/status/{orderId}")
    CommonResult getOrderStatus(@PathVariable("orderId") String orderId);
}
