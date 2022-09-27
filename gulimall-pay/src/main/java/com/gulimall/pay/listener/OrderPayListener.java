package com.gulimall.pay.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.gulimall.pay.config.AlipayTemplate;
import com.gulimall.pay.feign.OrderFeignService;
import com.gulimall.pay.service.PayOrderService;
import com.gulimall.pay.vo.PayAsyncVo;

@RestController
public class OrderPayListener {
	@Autowired
	OrderFeignService	orderFeignService;
	@Autowired
	AlipayTemplate		alipayTemplate;
	@Autowired
	PayOrderService		payOrderService;

	/**
	 * 支付成功异步通知
	 * @param payAsyncVo
	 * @param request
	 * @return
	 */
	@PostMapping("pay/notify")
	public String handleAlipay(PayAsyncVo payAsyncVo, HttpServletRequest request) throws AlipayApiException {
		System.out.println("收到支付宝异步通知**********");
		//只要收到支付宝的异步通知，返回success，支付宝便不再通知
		//todo:需要验证签名
		Map<String, String> params = new HashMap<>();
		Map<String, String[]> requestParameters = request.getParameterMap();
		for (String name : requestParameters.keySet()) {
			String[] values = requestParameters.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values + ",";
			}
			params.put(name, valueStr);
		}
		boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayTemplate.getAlipayPublicKey(), alipayTemplate.getCharset(), alipayTemplate.getSignType());
		if (signVerified) {

			/**
			 * 支付宝异步通知验签成功，修改订单状态
			 * 这个地方直接开始修改订单状态，而真正的需求需要根据thirdreqId与thirdresId与状态获取transDo,并对transDo与payDo的最终状态进行更新
			 */
			payOrderService.handlePayResult(payAsyncVo);
			return "success";
		} else {
			return "error";
		}
	}
}
