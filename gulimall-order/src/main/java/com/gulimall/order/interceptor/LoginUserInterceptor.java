package com.gulimall.order.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.gulimall.common.constant.SessionConstant;
import com.gulimall.common.vo.MemberRespVo;

/**
 * @author aqiang9  2020-09-24
 */
public class LoginUserInterceptor implements HandlerInterceptor {

	public static ThreadLocal<MemberRespVo> loginUser = new ThreadLocal<>();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		MemberRespVo attribute = (MemberRespVo) request.getSession().getAttribute(SessionConstant.USER_INFO_KEY);
		if (attribute == null) {
			// 提示去登录
			request.getSession().setAttribute("msg", "请先登录");
			//重定向
			response.sendRedirect("http://auth.gulimall.com/login.html?orginUrl=http://order.gulimall.com/confirm.html");
			return false;
		}
		loginUser.set(attribute);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}
}
