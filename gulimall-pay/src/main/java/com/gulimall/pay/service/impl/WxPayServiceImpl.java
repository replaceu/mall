package com.gulimall.pay.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import com.gulimall.common.constant.ICommonConstants;
import com.gulimall.common.utils.R;
import com.gulimall.pay.config.WxPayConfig;
import com.gulimall.pay.constants.PayConstants;
import com.gulimall.pay.dao.PayInfoDao;
import com.gulimall.pay.dto.*;
import com.gulimall.pay.entity.PayInfoEntity;
import com.gulimall.pay.feign.OrderFeignService;
import com.gulimall.pay.service.RefundPayService;
import com.gulimall.pay.service.WxPayService;
import com.gulimall.pay.vo.PayVo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WxPayServiceImpl implements WxPayService {
	@Autowired
	CloseableHttpClient	wxPayClient;
	@Autowired
	OrderFeignService	orderFeignService;
	@Autowired
	WxPayConfig			wxPayConfig;
	@Autowired
	PayInfoDao			payInfoDao;
	@Autowired
	RefundPayService	refundPayService;

	/**
	 * 创建订单，调用native支付接口
	 *
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

	/**
	 * 记录支付日志
	 * @param plainText
	 */
	@Override
	@Transactional
	public void recordPaymentInfo(String plainText) {
		Gson gson = new Gson();
		HashMap plainMap = gson.fromJson(plainText, HashMap.class);
		//订单号
		String orderId = (String) plainMap.get("out_trade_no");
		//业务编号
		String transactionId = (String) plainMap.get("transaction_id");
		//支付类型
		String tradeType = (String) plainMap.get("trade_type");
		//交易状态
		String tradeState = (String) plainMap.get("trade_state");
		//用户实际支付金额
		Map<String, Object> amount = (Map) plainMap.get("amount");
		Integer payerTotal = ((Double) amount.get("payer_total")).intValue();

		//todo:记录支付日志
		PayInfoEntity payInfoEntity = new PayInfoEntity();
		payInfoEntity.setOrderNo(orderId);
		payInfoEntity.setPaymentType(PayConstants.PayType.pay);
		payInfoEntity.setTransactionId(transactionId);
		payInfoEntity.setTradeType(tradeType);
		payInfoEntity.setTradeState(tradeState);
		payInfoEntity.setPayerTotal(payerTotal);
		payInfoEntity.setContent(plainText);
		payInfoDao.insert(payInfoEntity);
	}

	@Override
	public void userCancelOrderPay(String orderId) {
		//todo:调用微信支付的关单接口
		weixinPayCloseOrder(orderId);
		//todo:openFeign调用方法更改订单状态
		orderFeignService.updateOrderStatus(orderId, PayConstants.OrderStatus.cancel);
	}

	/**
	 * 查询订单支付状态
	 * @param orderId
	 * @return
	 */
	@Override
	public PayStatusDto queryOrderPayStatus(String orderId) {
		PayStatusDto retDto = new PayStatusDto();
		String url = String.format(PayConstants.WeixinApiType.queryPayStatus, orderId);
		url = wxPayConfig.getDomain().concat(url).concat("?mchid=").concat(wxPayConfig.getMchId());

		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept", "application/json");

		//完成签名并执行请求
		CloseableHttpResponse response = null;
		try {
			response = wxPayClient.execute(httpGet);
			String bodyAsString = EntityUtils.toString(response.getEntity());//响应体
			int statusCode = response.getStatusLine().getStatusCode();//响应状态码
			retDto.setOrderId(orderId);
			if (statusCode == 200) { //处理成功
				retDto.setOrderPayStatus(ICommonConstants.SUCCESS);
			} else if (statusCode == 204) { //处理成功，无返回Body
				retDto.setOrderPayStatus(ICommonConstants.SUCCESS);
			} else {
				throw new IOException("request failed");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return retDto;

	}

	@Override
	public void refundOrderPay(PayRefundDto payRefundDto) {
		//todo:根据订单编号创建退款单
		PayRefundInfoDto refundInfo = refundPayService.createRefundByOrderId(payRefundDto);
		//todo:调用退款API
		String url = wxPayConfig.getDomain().concat(PayConstants.WeixinApiType.domesticRefunds);
		HttpPost httpPost = new HttpPost(url);

		// 请求body参数
		Gson gson = new Gson();
		Map paramsMap = new HashMap();
		paramsMap.put("out_trade_no", refundInfo.getOrderId());//订单编号
		paramsMap.put("out_refund_no", refundInfo.getRefundId());//退款单编号
		paramsMap.put("reason", refundInfo.getReason());//退款原因
		paramsMap.put("notify_url", wxPayConfig.getNotifyDomain().concat(PayConstants.WeixinApiType.refundNotify));//退款通知地址

		Map amountMap = new HashMap();
		amountMap.put("refund", refundInfo.getRefundFee());//退款金额
		amountMap.put("total", refundInfo.getTotalFee());//原订单金额
		amountMap.put("currency", "CNY");//退款币种
		paramsMap.put("amount", amountMap);

		//将参数转换成json字符串
		String jsonParams = gson.toJson(paramsMap);
		log.info("请求参数 ===> {}" + jsonParams);

		StringEntity entity = new StringEntity(jsonParams, "utf-8");
		entity.setContentType("application/json");//设置请求报文格式
		httpPost.setEntity(entity);//将请求报文放入请求对象
		httpPost.setHeader("Accept", "application/json");//设置响应报文格式

		//完成签名并执行请求，并完成验签
		CloseableHttpResponse response = null;
		try {
			response = wxPayClient.execute(httpPost);
			//解析响应结果
			String bodyAsString = EntityUtils.toString(response.getEntity());
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				log.info("成功, 退款返回结果 = " + bodyAsString);
			} else if (statusCode == 204) {
				log.info("成功");
			} else {
				throw new RuntimeException("退款异常, 响应码 = " + statusCode + ", 退款返回结果 = " + bodyAsString);
			}
			//更新订单状态
			orderFeignService.updateOrderStatus(refundInfo.getOrderId(), PayConstants.OrderStatus.refund);
			//更新退款单
			refundPayService.updateRefundOrder(bodyAsString);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 关单接口
	 * @param orderId
	 */
	private void weixinPayCloseOrder(String orderId) {
		//todo:创建远程请求对象
		String url = String.format(PayConstants.WeixinApiType.closeOrderById, orderId);
		url = wxPayConfig.getDomain().concat(url);
		HttpPost httpPost = new HttpPost(url);
		//todo:组装JSON请求体
		Gson gson = new Gson();
		Map<String, String> paramsMap = new HashMap<>();
		paramsMap.put("mchid", wxPayConfig.getMchId());
		String jsonParams = gson.toJson(paramsMap);
		//将请求参数设置到请求对象中
		StringEntity entity = new StringEntity(jsonParams, "utf-8");
		entity.setContentType("application/json");
		httpPost.setEntity(entity);
		httpPost.setHeader("Accept", "application/json");

		try {
			//完成签名并执行请求
			CloseableHttpResponse response = wxPayClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();//响应状态码
			if (statusCode == 200) { //处理成功
				log.info(ICommonConstants.SUCCESS);
			} else if (statusCode == 204) { //处理成功，无返回Body
				log.info(ICommonConstants.SUCCESS);
			} else {
				log.info("Native下单失败,响应码 = " + statusCode);
				throw new IOException("request failed");
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
