package org.zerock.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.zerock.domain.ProductVO;

@Controller
@RequestMapping("/products")
public class AjaxTestController {
	Logger logger = LoggerFactory.getLogger(AjaxTestController.class);
	static Map<String,Object> map;
	static {
		map = new HashMap<>();
		map.put("AAA","1000");
		map.put("BBB","2000");
		map.put("CCC","3000");
	}
	
	
	
	@RequestMapping(value="/all",method=RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> getProducts() throws Exception {
		logger.info("getProducts()...GET");
		ResponseEntity<Map<String,Object>> entity = null;
		try {
			entity = new ResponseEntity<>(map,HttpStatus.OK);
		} catch (Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}		
		return entity;
	}
	
	
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> registerProduct(@RequestBody ProductVO vo) throws Exception {
		logger.info("register..post");
		logger.info(vo.toString());
		ResponseEntity<Map<String,Object>> entity = null;
		try {
			map.put(vo.getName(), vo.getPrice());
			//map.put(name, price);
			entity = new ResponseEntity<>(map,HttpStatus.OK);
		} catch (Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}		
		return entity;
	}
	
	@RequestMapping(value="/modify",method=RequestMethod.PUT)
	public ResponseEntity<Map<String,Object>> modifyProduct(@RequestBody String name,
																@RequestBody String price) throws Exception {
		
		logger.info("register..post");
		logger.info("name"+name);
		logger.info("price"+price);
		ResponseEntity<Map<String,Object>> entity = null;
		try {			
			map.remove(name);
			map.put(name, price);
			entity = new ResponseEntity<>(map,HttpStatus.OK);
		} catch (Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}		
		return entity;
	}
	
	
	
	
	

}
