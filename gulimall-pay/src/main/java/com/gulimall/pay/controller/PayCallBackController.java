package com.gulimall.pay.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gulimall.common.constant.ICommonConstants;
import com.gulimall.pay.service.PayCallbackService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "PayCallback", description = "支付回调")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class PayCallBackController {
	@Autowired
	PayCallbackService payCallbackService;

	@ApiOperation(value = "alipay支付回调", notes = "aliApp支付回调")
	@PostMapping(value = ICommonConstants.MappingConstants.aliPayCallback)
	public String doWeixinPayCallback(HttpServletRequest request, HttpServletResponse response) {
		return payCallbackService.doWeixinPayCallback(request, response);
	}
}
