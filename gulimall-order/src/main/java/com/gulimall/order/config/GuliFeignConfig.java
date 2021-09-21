package com.gulimall.order.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @author Carter
 * @date 2021-08-29 23:51
 * @description:用以解决feign请求头丢失的问题
 * @version:
 */
@Configuration
public class GuliFeignConfig {

	@Bean("requestInterceptor")
	public RequestInterceptor requestInterceptor() {
		return new RequestInterceptor() {
			@Override
			public void apply(RequestTemplate requestTemplate) {
				//1.RequestContextHolder拿到刚进来的请求数据
				ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
				HttpServletRequest request = attributes.getRequest();//老请求
				if (request != null) {
					//2.同步请求头数据
					//在异步的情况下，仍然会出现不同的线程之间不能够得到ThreadLocal的请求数据
					String cookie = request.getHeader("Cookie");
					//3.给新请求同步了老请求的cookie
					requestTemplate.header("Cookie", cookie);
				}
			}
		};
	}
}
