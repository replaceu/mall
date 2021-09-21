package com.gulimall.cart.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.gulimall.cart.interceptor.CartInterceptor;

/**
 * @author aqiang9  2020-09-08
 */
@Configuration
public class WebConfig {
	@Bean
	public WebMvcConfigurer webMvcConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				registry.addInterceptor(new CartInterceptor()).addPathPatterns("/**");
			}
		};
	}
}
