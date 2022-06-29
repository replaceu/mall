package com.gulimall.secondKill.interceptor;

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
		boolean match = matcher.match("/kill", requestURI);
		//如果是秒杀，需要判断是否登录，其他路径直接放行
		if (match) {
			MemberRespVo attribute = (MemberRespVo) request.getSession().getAttribute(SessionConstant.USER_INFO_KEY);
			if (attribute != null) {
				loginUser.set(attribute);
				return true;
			} else {
				request.getSession().setAttribute("msg", "请先进行登录");
				response.sendRedirect("http://auth.gulimall.com/login.html");
				return false;
			}
		}
		return true;

	}
}
