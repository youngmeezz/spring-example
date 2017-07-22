package com.test.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.domain.TestDomain;

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
	
	@RequestMapping(value="/mappings", method=RequestMethod.GET)
	public String mappingTestGet() {		
		return "mappingTest";
	}
	
	@RequestMapping(value="/mappings", method=RequestMethod.POST)
	public String mappingTestPost(Map<String,String> mappingMap) {
		if(mappingMap == null || mappingMap.size()==0) {
			logger.info("## [mapping is null or empty]");
		} else {
			mappingMap.forEach((k,v)->{
				logger.info("## [key : " + k + ", value = " + v + "]");				
			});
		}
		return "mappingTest";
	}
}
