package com.gulimall.pay.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface PayCallbackService {
	String doWeixinPayCallback(HttpServletRequest request, HttpServletResponse response);
}
