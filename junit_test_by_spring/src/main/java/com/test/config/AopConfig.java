package com.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.test.aop.Audience1;
import com.test.aop.Audience2;
import com.test.aop.Audience3;
import com.test.service.Performance;

@Configuration
@EnableAspectJAutoProxy //Enable AspectJ auto-proxying
@ComponentScan  
public class AopConfig {	
	@Bean
	public Audience1 audience1() {
		return new Audience1();		
	}
	
	@Bean
	public Audience2 audience2() {
		return new Audience2();
	}
	
	@Bean
	public Audience3 audience3() {
		return new Audience3();
	}
	
	@Bean
	public Performance performance() {
		return new Performance();
	}
	
}
