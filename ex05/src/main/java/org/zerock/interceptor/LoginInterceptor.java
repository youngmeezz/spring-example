package org.zerock.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	private static final String LOGIN = "login";
	private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response,Object handler) throws Exception {			
		logger.info("LoginInterceptor.preHandler");		
		HttpSession session = request.getSession();
		if(session.getAttribute(LOGIN) != null) {
			logger.info("clear login data before");
			session.removeAttribute(LOGIN);
		}		
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request,
					HttpServletResponse response,Object handler,
					ModelAndView modelAndView) throws Exception {		
		logger.info("LoginInterceptor.postHandler");
		
		HttpSession session = request.getSession();		
		ModelMap modelMap = modelAndView.getModelMap();
		Object userVO = modelMap.get("userVO");
		
		if(userVO != null) { //로그인 성공
			logger.info("new login success");
			session.setAttribute(LOGIN,userVO);			
			if(request.getParameter("useCookie") != null) { //자동로그인 체크(쿠키사용)				
				logger.info("remember me...");
				//key : "loginCookie" , value : 세션 아이디(세션 쿠키,made by Tomcat)
				Cookie loginCookie = new Cookie("loginCookie",session.getId());
				loginCookie.setPath("/"); // '/'밑의 모든 경로에서 쿠키가 visible
				loginCookie.setMaxAge(60*60*24*7); //일주일
				response.addCookie(loginCookie);
			}						
			//response.sendRedirect("/");
			//intercept 전의 요청 URI가 있으면 redirect
			Object dest = session.getAttribute("dest");
			response.sendRedirect(dest !=null ? (String)dest:"/");			
		}
	}
	
				
	
	
	

}





