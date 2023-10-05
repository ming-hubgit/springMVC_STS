package com.office.library.user.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class UserMemberLoginInterceptor extends HandlerInterceptorAdapter {
	//로그인 인증 처리하기
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession(false);
		if(session != null) {
			Object object = session.getAttribute("loginedUserMemberVO");
			if(object != null) {
				return true;
			}
		}
		response.sendRedirect(request.getContextPath() + "/user/member/loginForm");
		return false;
	}
}
