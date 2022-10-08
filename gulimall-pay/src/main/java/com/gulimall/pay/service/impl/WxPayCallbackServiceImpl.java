package com.gulimall.pay.service.impl;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.gulimall.pay.service.WxPayCallbackService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WxPayCallbackServiceImpl implements WxPayCallbackService {
	@Override
	public String weixinNativePyCallback(HttpServletRequest request, HttpServletResponse response) {
		Gson gson = new Gson();
		Map<String, String> map = new HashMap<>();
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

			//todo:处理订单

			//todo:成功应答状态码必须为200或者204，否则就是应答失败
			response.setStatus(200);
			map.put("code", "success");
			map.put("message", "成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(map);
	}
}
