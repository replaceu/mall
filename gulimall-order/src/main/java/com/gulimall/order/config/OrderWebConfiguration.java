package com.gulimall.order.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.gulimall.order.interceptor.LoginUserInterceptor;

/**
 * @author Carter
 * @date 2021-08-29 03:26
 * @description:
 * @version:
 */

@Configuration
public class OrderWebConfiguration implements WebMvcConfigurer {
	@Autowired
	LoginUserInterceptor interceptor;

	//添加拦截器
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor).addPathPatterns("/**");
	}
}
