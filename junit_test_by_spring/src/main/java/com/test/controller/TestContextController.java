package com.test.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/context/menu/**")
@Controller
public class TestContextController {
	private static final Logger logger = LoggerFactory.getLogger(TestContextController.class);
	private static List<String> list;
	static {		
		list = new ArrayList<>();
		for(int i=0; i<10; i++) {
			list.add("test"+i);
		}		
	}
	//index page
	@RequestMapping(value="/list", method=RequestMethod.GET) 
	public String listPage2() {
		logger.info("## [request listPage /list]");
		return "listPage";
	}
	
	// Read ALL
	@ResponseBody
	@RequestMapping(value="/all", method=RequestMethod.GET) 
	public ResponseEntity<List<String>> getList() {
		logger.info("## [request list]");
		return new ResponseEntity<List<String>>(list,HttpStatus.OK);
	}
	
	// C
	@ResponseBody
	@RequestMapping(value="/", method=RequestMethod.POST) 
	public ResponseEntity<String> add(@RequestBody String text) {
		logger.info("## [add] text : {}", text);
		list.add(text);
		return new ResponseEntity<>("SUCCESS",HttpStatus.OK);
	}
	
	// R
	@ResponseBody
	@RequestMapping(value="/{id}", method=RequestMethod.GET) 
	public ResponseEntity<String> add(@PathVariable("id") Integer id) {
		logger.info("## [read] id : {}", id);		
		return new ResponseEntity<>(list.get(id),HttpStatus.OK);
	}
	
	// U
	
	// D
	@ResponseBody
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE) 
	public ResponseEntity<String> getList(@PathVariable("id") Integer id) {
		logger.info("## [request delete] id : {}",id);
		list.remove(id);
		return new ResponseEntity<>("SUCCESS",HttpStatus.OK);
	}
	
	
	
	
	
	
	
	

}



















