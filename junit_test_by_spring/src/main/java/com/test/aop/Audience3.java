package com.test.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Create around advice
 * 
 * @author zaccoding
 * @date 2017. 7. 1.
 */
@Aspect
public class Audience3 {
	
	@Pointcut("execution(** com.test.service.Performance.perform(..))")
	public void performance() {}
	
	@Around("performance()")
	public void watchPerformance(ProceedingJoinPoint jp) {
		try {
			System.out.println("Audience3.Silencing cell phones");
			System.out.println("Audience3.Taking seats");
			jp.proceed();
			System.out.println("Audience3.CLAP CLAP CLAP!!!");			
		} catch(Throwable e) {
			System.out.println("Audience3.Demanding a refund");
		}
		
	}	
}
