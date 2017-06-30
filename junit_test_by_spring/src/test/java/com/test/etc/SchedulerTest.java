package com.test.etc;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchedulerTest {
	static Logger logger;
	static ScheduledExecutorService scheduler;
	static int initDelay,period,delay;
	static long sleepTime;
	static TimeUnit timeUnit;	
	
	@BeforeClass
	public static void setUp() {
		logger = LoggerFactory.getLogger(SchedulerTest.class);
		scheduler = Executors.newScheduledThreadPool(1);
		initDelay = 1;
		period = 5;
		delay = 20;
		timeUnit = TimeUnit.SECONDS;
		sleepTime = TimeUnit.MICROSECONDS.convert(delay+5,timeUnit);
	}
	
	@Test
	public void printTest() throws Exception {
		final Runnable printer = new Runnable() {
			public void run() {
				logger.info("printer!!");
			}
		};		
		
		
		final ScheduledFuture<?> printerHandle 
				= scheduler.scheduleAtFixedRate(printer, initDelay, period,timeUnit);

		logger.info("delay : " + printerHandle.getDelay(timeUnit));

		scheduler.schedule(new Runnable() {
			public void run() {
				logger.info("cancel!!");
				printerHandle.cancel(true);
			}
		}, delay, TimeUnit.SECONDS);

		Thread.sleep(sleepTime);
	}
	
	
}
