package org.zerock.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SampleInterceptor extends HandlerInterceptorAdapter {	
	/*
	 * preHandler : 지정된 컨트롤러의 동작 이전에 가로채는 역할로 사용
	 * param : Object handler == 현재 실행하려는 메소드 자체 
	 * return : 다음 interceptor나 대상 컨트롤러를 호출할 것인지 결정 
	 */
	@Override
	public boolean preHandle(HttpServletRequest requset,
			HttpServletResponse response,Object handler) throws Exception {
		
		System.out.println("-------------pre handle...-------------");		
		HandlerMethod method = (HandlerMethod)handler;
		Method methodObj = method.getMethod();
		
		System.out.println("Bean : "+method.getBean());		
		System.out.println("Method : "+methodObj);	
		
		System.out.println("---------------------------------------");		
		return true;
	}
	
	//지정된 컨트롤러의 동작 이후에 처리
	//Spring MVC의 Front Controller인 DispatcherServlet이 화면을 처리하기 전에 동작
	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response,
						Object handler,ModelAndView modelAndView) throws Exception{
		System.out.println("-------------post handle...-------------");
		System.out.println("modelAndView.getViewName() : "+modelAndView.getViewName());		
		Object result = modelAndView.getModel().get("result");
		
		if(result != null) {
			System.out.println("result : "+result);
			request.getSession().setAttribute("result",result);
			response.sendRedirect("/doA");
		}
		System.out.println("---------------------------------------");		
		
	}
	

	//afterCompletion : DispatcherServlet의 화면 처리가 완료된 상태에서 처리
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}
	
	
		

}
