package com.gulimall.pay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.TypeReference;
import com.alipay.api.AlipayApiException;
import com.gulimall.common.utils.R;
import com.gulimall.pay.config.AlipayTemplate;
import com.gulimall.pay.feign.OrderFeignService;
import com.gulimall.pay.vo.PayVo;

import io.swagger.annotations.Api;

@RestController
@Api(value = "Pay", description = "支付")
public class PayController {
	@Autowired
	AlipayTemplate		alipayTemplate;
	@Autowired
	OrderFeignService	orderFeignService;

	@ResponseBody
	@GetMapping("/payOrder")
	public String payOrderAlipay(@RequestParam("orderSn") String orderSn) throws AlipayApiException {
		R orderPay = orderFeignService.getOrderPay(orderSn);
		PayVo orderPayData = orderPay.getData(new TypeReference<PayVo>() {
		});

		String pay = alipayTemplate.pay(orderPayData);
		return pay;
	}

}
