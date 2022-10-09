package com.gulimall.pay.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface WxPayCallbackService {
	String weixinNativePyCallback(HttpServletRequest request, HttpServletResponse response);

	String weixinDecrypt(Map<String, Object> map);
}
