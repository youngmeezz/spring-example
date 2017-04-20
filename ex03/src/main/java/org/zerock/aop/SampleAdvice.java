package org.zerock.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component //스프링의 빈으로 인식되기 위해 설정
@Aspect //AOP 기능을 하는 클래스의 선언에 추가
public class SampleAdvice {
	private static final Logger logger = LoggerFactory.getLogger(SampleAdvice.class);
	
	//@Before == 해당 메소드를 먼저 실행한 후 target 메소드가 실행
	//execution == Pointcut을 지정하는 문법(AspectJ문법) 
	//=> 'org.zerock.service.MessageService'로 시작하는 모든 클래스의 '*'(모든)메소드 지정
	@Before("execution(* org.zerock.service.MessageService*.*(..))")	
	public void startLog(JoinPoint jp) {
		logger.info("---------------------------");
		logger.info("---------------------------");
		
		//특정한 컨트롤러를 호출할 때 전달되는 파라미터를 확인해주는 메소드
		//org.aspectj.lang.JoinPoint 이용
		//=>API 보기
		logger.info(Arrays.toString(jp.getArgs()));
	}
	
	//@Around : 메소드의 실행 전체를 앞,뒤로 감싸서 특정한 기능을 실행할 수 있음
	@Around("execution(* org.zerock.service.MessageService*.*(..))")
	public Object timeLog(ProceedingJoinPoint pjp)throws Throwable {
		long startTime = System.currentTimeMillis();
		logger.info(Arrays.toString(pjp.getArgs()));
		
		Object result = pjp.proceed(); //다음 Advice를 실행하거나, 실제 target 객체의 메소드를 실행하는 기능
		
		long endTime = System.currentTimeMillis();
		logger.info(pjp.getSignature().getName() + " : " + (endTime-startTime));
		logger.info("=======================================================");
		
		return result;
	}
 
	
	
	
}
