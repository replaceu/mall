package com.gulimall.pay.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.github.wxpay.sdk.WXPayConfig;
import com.google.gson.Gson;
import com.gulimall.common.utils.R;
import com.gulimall.pay.config.WxPayConfig;
import com.gulimall.pay.constants.PayConstants;
import com.gulimall.pay.dto.CreatePayReqDto;
import com.gulimall.pay.dto.OrderDto;
import com.gulimall.pay.dto.WeixinPayResDto;
import com.gulimall.pay.feign.OrderFeignService;
import com.gulimall.pay.service.WxPayService;
import com.gulimall.pay.vo.PayAsyncVo;
import com.gulimall.pay.vo.PayVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;

@Service
@Slf4j
public class WxPayServiceImpl implements WxPayService {
	@Autowired
	CloseableHttpClient	wxPayClient;
	@Autowired
	OrderFeignService	orderFeignService;
	@Autowired
	WxPayConfig			wxPayConfig;

	/**
	 * 创建订单，调用native支付接口
	 * @param createPayReq
	 * @return
	 */
	@Override
	public WeixinPayResDto weixinNativePay(CreatePayReqDto createPayReq) {
		WeixinPayResDto retDto = new WeixinPayResDto();
		//生成支付订单
		OrderDto order = new OrderDto();
		R orderPay = orderFeignService.getOrderPay(createPayReq.getOrderId());
		PayVo data = orderPay.getData(new TypeReference<PayVo>() {
		});

		order.setTitle(data.getSubject());
		order.setOrderId(data.getOutTradeNo());
		order.setTotalAmount(data.getTotalAmount());
		order.setOrderStatus(PayConstants.OrderStatus.notPay);
		//todo:调用统一下单API
		HttpPost httpPost = new HttpPost(wxPayConfig.getDomain().concat(PayConstants.PayChannel.nativePay));
		//设置请求body参数
		Gson gson = new Gson();
		HashMap<String, Object> paramMap = new HashMap<>();
		paramMap.put("appid", wxPayConfig.getAppid());
		paramMap.put("mchid", wxPayConfig.getMchId());
		paramMap.put("description", order.getTitle());
		paramMap.put("out_trade_no", order.getOrderId());
		paramMap.put("notify_url", wxPayConfig.getNotifyDomain().concat(PayConstants.NotifyType.nativeNotify));

		HashMap<String, String> amountMap = new HashMap<>();
		amountMap.put("total", order.getTotalAmount());
		amountMap.put("currency", "CNY");
		paramMap.put("amount", amountMap);
		//将参数转换成为JSON字符串
		String paramJson = gson.toJson(paramMap);
		StringEntity entity = new StringEntity(paramJson, "utf-8");
		entity.setContentType("application/json");
		httpPost.setEntity(entity);
		httpPost.setHeader("Accept", "application/json");
		//todo:完成签名并执行请求
		try {
			CloseableHttpResponse response = wxPayClient.execute(httpPost);
			//响应体
			String body = EntityUtils.toString(response.getEntity());
			//响应状态码
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) { //处理成功 
				log.info("成功, 返回结果 = " + body);
			} else if (statusCode == 204) {
				//处理成功，无返回Body 
				log.info("成功");
			} else {
				log.info("Native下单失败,响应码=" + statusCode + ",返回结果 = " + body);
				throw new IOException("request failed");
			}
			//todo:返回用于支付的二维码以及订单号
			HashMap<String, String> resultMap = gson.fromJson(body, HashMap.class);
			String codeUrl = resultMap.get("code_url");
			retDto.setNativePay(codeUrl);
			retDto.setOrderId(order.getOrderId());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return retDto;

	}
}
