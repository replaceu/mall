package com.gulimall.member.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import com.gulimall.common.constant.SessionConstant;
import com.gulimall.common.vo.MemberRespVo;

@Component
public class LoginUserInterceptor implements HandlerInterceptor {
	public static ThreadLocal<MemberRespVo> loginUser = new ThreadLocal<MemberRespVo>();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String requestURI = request.getRequestURI();
		AntPathMatcher matcher = new AntPathMatcher();
		boolean status = matcher.match("/order/order*status/**", requestURI);
		boolean pay = matcher.match("pay/**", requestURI);
		if (status || pay) { return true; }

		MemberRespVo attribute = (MemberRespVo) request.getSession().getAttribute(SessionConstant.USER_INFO_KEY);
		if (attribute != null) {
			loginUser.set(attribute);
			return true;
		} else {
			//没登录就去登录
			request.getSession().setAttribute("msg", "请先进行登录");
			response.sendRedirect("http://auth.gulimall.com/login.html");
			return false;
		}
	}
}
