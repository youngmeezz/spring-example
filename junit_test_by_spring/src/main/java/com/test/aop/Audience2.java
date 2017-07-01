package com.test.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * using @Pointcut
 * 
 * @author zaccoding
 * @date 2017. 7. 1.
 */
@Aspect
public class Audience2 {
	@Pointcut("execution(** com.test.service.Performance.perform(..))")
	public void performance(){}
	
	/** Before performance */
	@Before( "execution(** com.test.service.Performance.perform(..))" )
	public void silenceCellPhones() {
		System.out.println("Audience2.Silencing cell phones");
	}
	
	/** Before performance */
	@Before("execution(** com.test.service.Performance.perform(..))")
	public void takeSeats() {
		System.out.println("Audience2.Taking seats");
	}
	
	/** After performance */
	@AfterReturning("execution(** com.test.service.Performance.perform(..))")
	public void applause() {
		System.out.println("Audience2.CLAP CLAP CLAP!!!");
	}
	
	/** Before performance */
	@AfterThrowing("execution(** com.test.service.Performance.perform(..))")
	public void demandRefund() {
		System.out.println("Audience2.Demanding a refund");
	}
}
