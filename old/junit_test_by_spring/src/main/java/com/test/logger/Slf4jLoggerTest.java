package com.test.logger;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jLoggerTest {	
	private static final Logger logger = LoggerFactory.getLogger(Slf4jLoggerTest.class);	
	
	@Test
	public void format() {
		logger.info("param1 : {}, param2 : {} , param3 : {} ", new Object[]{"test1",Integer.valueOf(10),new String("params")});
		logger.error("param1 : {}, param2 : {} , param3 : {} ", 
					//new Object[]{"test1",Integer.valueOf(10),new String("params")}
					new RuntimeException("test exception"));
		
		
	}
	

}
