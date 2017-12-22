package com.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.test.domain.TestBean;

@Configuration
public class TestConfig {	
	@Bean(initMethod="init")
	public TestBean testBean() {
		return new TestBean();
	}
}
