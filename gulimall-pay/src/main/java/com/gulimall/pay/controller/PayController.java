package com.gulimall.pay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gulimall.common.utils.R;
import com.gulimall.pay.constants.PayConstants;
import com.gulimall.pay.dto.CreatePayReqDto;
import com.gulimall.pay.dto.PayRefundDto;
import com.gulimall.pay.dto.PayStatusDto;
import com.gulimall.pay.dto.WeixinPayResDto;
import com.gulimall.pay.service.WxPayService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

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
	public R weixinNative(@PathVariable CreatePayReqDto createPayReq) {
		//todo:发起支付请求
		WeixinPayResDto payRes = wxPayService.weixinNativePay(createPayReq);
		return R.ok().setData(payRes);
	}

	@ApiOperation("记录支付日志")
	@PostMapping(PayConstants.MappingConstants.recordPaymentInfo)
	public void recordPaymentInfo(String plainText) {
		wxPayService.recordPaymentInfo(plainText);
	}

	@ApiOperation("用户取消订单")
	@PostMapping(PayConstants.MappingConstants.userCancelOrderPay)
	public R userCancelOrderPay(@PathVariable String orderId) {
		wxPayService.userCancelOrderPay(orderId);
		return R.ok().setData(orderId);
	}

	@ApiOperation("查询订单支付状态")
	@GetMapping(PayConstants.MappingConstants.queryOrderPayStatus)
	public R queryOrderPayStatus(@PathVariable String orderId) {
		PayStatusDto orderStatus = wxPayService.queryOrderPayStatus(orderId);
		return R.ok().setData(orderStatus);
	}

	@ApiOperation("用户申请退款")
	@PostMapping(PayConstants.MappingConstants.refundOrderPay)
	public R refundOrderPay(PayRefundDto payRefundDto) {
		wxPayService.refundOrderPay(payRefundDto);
		return R.ok();
	}

}
