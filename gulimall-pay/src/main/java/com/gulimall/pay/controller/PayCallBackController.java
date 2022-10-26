package com.gulimall.pay.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gulimall.common.constant.ICommonConstants;
import com.gulimall.pay.constants.PayConstants;
import com.gulimall.pay.service.PayCallbackService;
import com.gulimall.pay.service.WxPayCallbackService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "PayCallback", description = "支付回调")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class PayCallBackController {
	@Autowired
	PayCallbackService		payCallbackService;
	@Autowired
	WxPayCallbackService	wxPayCallbackService;

	@ApiOperation(value = "alipay支付回调", notes = "aliApp支付回调")
	@PostMapping(value = ICommonConstants.MappingConstants.aliPayCallback)
	public String doWeixinPayCallback(HttpServletRequest request, HttpServletResponse response) {
		return payCallbackService.doWeixinPayCallback(request, response);
	}

	@ApiOperation(value = "微信支付回调", notes = "微信支付回调")
	@PostMapping(value = PayConstants.MappingConstants.weixinNativePyCallback)
	public String weixinNativePayCallback(HttpServletRequest request, HttpServletResponse response) {
		return wxPayCallbackService.weixinNativePayCallback(request, response);
	}

	@ApiOperation(value = "微信退款回调", notes = "微信退款回调")
	@PostMapping(value = PayConstants.MappingConstants.weixinRefundCallback)
	public String weixinRefundCallback(HttpServletRequest request, HttpServletResponse response) {
		return wxPayCallbackService.weixinRefundCallback(request, response);
	}

	@ApiOperation(value = "微信支付解密报文", notes = "解密报文")
	@PostMapping(value = PayConstants.MappingConstants.weixinDecrypt)
	public String weixinDecrypt(Map<String, Object> map) {
		return wxPayCallbackService.weixinDecrypt(map);
	}

}
