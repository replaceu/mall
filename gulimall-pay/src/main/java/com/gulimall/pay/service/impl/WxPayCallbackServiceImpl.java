package com.gulimall.pay.service.impl;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.gulimall.pay.config.WxPayConfig;
import com.gulimall.pay.feign.OrderFeignService;
import com.gulimall.pay.service.WxPayCallbackService;
import com.gulimall.pay.utils.ValidatorUtils;
import com.wechat.pay.contrib.apache.httpclient.auth.Verifier;
import com.wechat.pay.contrib.apache.httpclient.util.AesUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WxPayCallbackServiceImpl implements WxPayCallbackService {
	@Autowired
	Verifier			verifier;
	@Autowired
	WxPayConfig			wxPayConfig;
	@Autowired
	OrderFeignService	orderFeignService;

	@Override
	public String weixinNativePayCallback(HttpServletRequest request, HttpServletResponse response) {
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<>();
		try {
			BufferedReader br = null;
			StringBuilder result = new StringBuilder();
			br = request.getReader();
			for (String line; (line = br.readLine()) != null;) {
				if (result.length() > 0) {
					result.append("\n");
				}
				result.append(line);
			}
			String body = result.toString();
			HashMap bodyMap = gson.fromJson(body, HashMap.class);
			log.info("支付通知的id--> {}", bodyMap.get("id"));
			log.info("支付通知的完整数据--> {}", body);
			//todo:签名的验证
			String requestId = (String) bodyMap.get("id");
			ValidatorUtils validatorRequest = new ValidatorUtils(verifier, requestId, body);
			if (!validatorRequest.validate(request)) {
				log.error("通知验签失败");
				response.setStatus(500);
				map.put("code", "ERROR");
				map.put("message", "通知验签失败");
				return gson.toJson(map);
			}
			log.info("通知验签成功");
			//todo:处理订单
			orderFeignService.processOrder(map);
			//todo:成功应答状态码必须为200或者204，否则就是应答失败
			response.setStatus(200);
			map.put("code", "success");
			map.put("message", "成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(map);
	}

	/**
	 * 解密报文
	 * @param map
	 * @return
	 */
	@Override
	public String weixinDecrypt(Map<String, Object> map) {
		//todo:通知数据
		Map<String, String> resourceMap = (Map) map.get("resource");
		//todo:数据密文
		String ciphertext = resourceMap.get("ciphertext");
		//todo:随机串
		String nonce = resourceMap.get("nonce");
		//todo:附加数据
		String associatedData = resourceMap.get("associated_data");
		log.info("密文 ===> {}", ciphertext);
		AesUtil aesUtil = new AesUtil(wxPayConfig.getApiV3Key().getBytes(StandardCharsets.UTF_8));
		String plainText = null;
		try {
			plainText = aesUtil.decryptToString(associatedData.getBytes(StandardCharsets.UTF_8), nonce.getBytes(StandardCharsets.UTF_8), ciphertext);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		log.info("明文 ===> {}", plainText);
		return plainText;
	}

	@Override
	public String weixinRefundCallback(HttpServletRequest request, HttpServletResponse response) {
		//todo:退款通知执行
		Gson gson = new Gson();
		Map<String, String> map = new HashMap<>();
		try {
			//todo:处理通知参数
			BufferedReader br = null;
			StringBuilder result = new StringBuilder();
			br = request.getReader();
			for (String line; (line = br.readLine()) != null;) {
				if (result.length() > 0) {
					result.append("\n");
				}
				result.append(line);
			}
			String body = result.toString();
			Map<String, Object> bodyMap = gson.fromJson(body, HashMap.class);
			String requestId = (String) bodyMap.get("id");
			log.info("支付通知的id ===> {}", requestId);

			//签名的验证
			ValidatorUtils validatorRequest = new ValidatorUtils(verifier, requestId, body);
			if (!validatorRequest.validate(request)) {
				log.error("通知验签失败");
				//失败应答
				response.setStatus(500);
				map.put("code", "ERROR");
				map.put("message", "通知验签失败");
				return gson.toJson(map);
			}
			log.info("通知验签成功");
			//处理退款单
			orderFeignService.processRefund(bodyMap);
			//成功应答
			response.setStatus(200);
			map.put("code", "SUCCESS");
			map.put("message", "成功");
			return gson.toJson(map);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(map);
	}
}
