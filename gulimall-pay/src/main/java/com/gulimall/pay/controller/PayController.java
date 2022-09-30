package com.gulimall.pay.controller;

import com.gulimall.pay.constants.PayConstants;
import com.gulimall.pay.dto.CreatePayReqDto;
import com.gulimall.pay.dto.WeixinPayResDto;
import com.gulimall.pay.service.WxPayService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.TypeReference;
import com.alipay.api.AlipayApiException;
import com.gulimall.common.utils.R;
import com.gulimall.pay.config.AlipayTemplate;
import com.gulimall.pay.feign.OrderFeignService;
import com.gulimall.pay.vo.PayVo;

import io.swagger.annotations.Api;

@RestController
@Api(value = "Pay", description = "支付")
@Slf4j
@CrossOrigin
@RequestMapping(PayConstants.weixinPay)
public class PayController {
	@Autowired
	WxPayService wxPayService;

	/**
	 * native下单
	 */
	@ApiOperation("调用统一下单API,生成支付二维码")
	@PostMapping(PayConstants.MappingConstants.weixinNative)
	public R weixinNative(@PathVariable CreatePayReqDto createPayReq){
		//todo:发起支付请求
		WeixinPayResDto payRes = wxPayService.weixinNativePay(createPayReq);
		return R.ok().setData(payRes);
	}

}
