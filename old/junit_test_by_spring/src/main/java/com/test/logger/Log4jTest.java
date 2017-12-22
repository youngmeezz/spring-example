package com.test.logger;

import static org.junit.Assert.assertNotNull;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

public class Log4jTest {
	static Logger logger;
	
	@BeforeClass
	public static void setUp() {
		Logger.getLogger(Log4jTest.class).setLevel(Level.ALL);
		logger = Logger.getLogger(Log4jTest.class);		
	}
	
	@Test
	public void loggerTest() {		
		assertNotNull(logger);
		logger.trace("Trace message");
		logger.debug("Debug message");
		logger.info("Info message");
		logger.warn("Warn Message");
		logger.error("Error Mesasge");
		logger.fatal("Fatal Message");
	} 
	
	/*
	
	ref : https://www.tutorialspoint.com/log4j/log4j_logging_levels.htm 
	
	ALL < 
	 
	## "all" : All levels including custom levels.
	TRACE: com.dogsound.app.AppTest - Trace message
	DEBUG: com.dogsound.app.AppTest - Debug message
	INFO : com.dogsound.app.AppTest - Info message
	WARN : com.dogsound.app.AppTest - Warn Message
	ERROR: com.dogsound.app.AppTest - Error Mesasge
	FATAL: com.dogsound.app.AppTest - Fatal Message
	
	## "DEBUG" : Designates fine-grained informational events that are most useful to debug an application.
	DEBUG: com.dogsound.app.AppTest - Debug message
	INFO : com.dogsound.app.AppTest - Info message
	WARN : com.dogsound.app.AppTest - Warn Message
	ERROR: com.dogsound.app.AppTest - Error Mesasge
	FATAL: com.dogsound.app.AppTest - Fatal Message
	
	## "ERROR" : Designates error events that might still allow the application to continue running.
	ERROR: com.dogsound.app.AppTest - Error Mesasge
	FATAL: com.dogsound.app.AppTest - Fatal Message

	## "FATAL" : Designates very severe error events that will presumably lead the application to abort.
	FATAL: com.dogsound.app.AppTest - Fatal Message
	
	## "INFO" : Designates informational messages that highlight the progress of the application at coarse-grained level.
	INFO : com.dogsound.app.AppTest - Info message
	WARN : com.dogsound.app.AppTest - Warn Message
	ERROR: com.dogsound.app.AppTest - Error Mesasge
	FATAL: com.dogsound.app.AppTest - Fatal Message
	
	## "OFF" : The highest possible rank and is intended to turn off logging.
	//empty
	
	## "TRACE" : Designates finer-grained informational events than the DEBUG.
	
	TRACE: com.dogsound.app.AppTest - Trace message
	DEBUG: com.dogsound.app.AppTest - Debug message
	INFO : com.dogsound.app.AppTest - Info message
	WARN : com.dogsound.app.AppTest - Warn Message
	ERROR: com.dogsound.app.AppTest - Error Mesasge
	FATAL: com.dogsound.app.AppTest - Fatal Message
	
	## "WARN" : Designates potentially harmful situations.
	WARN : com.dogsound.app.AppTest - Warn Message
	ERROR: com.dogsound.app.AppTest - Error Mesasge
	FATAL: com.dogsound.app.AppTest - Fatal Message

	 
	 
	  */
	
}
