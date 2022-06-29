package com.gulimall.member.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * 因为订单服务做了用户登录的拦截，所以远程调用订单服务需要用户信息，我们给它共享cookies
 */
@Configuration
public class GuliFeignConfig {
	@Bean("requestInterceptor")
	public RequestInterceptor requestInterceptor() {
		return new RequestInterceptor() {
			@Override
			public void apply(RequestTemplate requestTemplate) {
				System.out.println("requestInterceptor线程" + Thread.currentThread().getId());
				//1.RequestContextHolder拿到刚进来的请求
				ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
				if (requestAttributes != null) {
					//老请求
					HttpServletRequest request = requestAttributes.getRequest();
					if (request != null) {
						//同步请求头数据cookie
						String cookie = request.getHeader("Cookie");
						//给新情求同步了老请求的cookie
						requestTemplate.header("Cookie", cookie);
					}

				}
			}
		};
	}
}
