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

/*
 * execution(수식어패턴? 리턴타입패턴 클래스이름패턴?메소드이름패턴(파라미터패턴)
 * '*' == 모든 값
 * '..' == 0개 이상
 */
@Component
@Aspect
public class SampleAdvice {

  private static final Logger logger = LoggerFactory.getLogger(SampleAdvice.class);

  //@Before("execution(* org.zerock.service.MessageService*.*(..))")
  public void startLog(JoinPoint jp) {

    logger.info("----------------------------");
    logger.info("----------------------------");
    logger.info(Arrays.toString(jp.getArgs()));

  }
  
  
 // @Around("execution(* org.zerock.service.MessageService*.*(..))")
  public Object timeLog(ProceedingJoinPoint pjp)throws Throwable{
    
    long startTime = System.currentTimeMillis();
    logger.info(Arrays.toString(pjp.getArgs()));
    
    Object result = pjp.proceed();
    
    long endTime = System.currentTimeMillis();
    logger.info( pjp.getSignature().getName()+ " : " + (endTime - startTime) );
    logger.info("=============================================");
    
    return result;
  }   

}
