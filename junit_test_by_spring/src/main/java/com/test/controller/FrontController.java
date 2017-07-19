package com.test.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FrontController {
	private static final Logger logger = LoggerFactory.getLogger(FrontController.class);
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(){
		System.out.println(logger.isDebugEnabled());
		System.out.println(logger.toString());
		logger.debug("## [request login GET]");
		logger.info("## [request login GET]");
		return "login";
	}
	
	@RequestMapping(value="/admin/test", method=RequestMethod.GET)
	public String test() {		
		return "adminTest";
	}

}
