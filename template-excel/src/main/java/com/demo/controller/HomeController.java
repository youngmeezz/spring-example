package com.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.demo.domain.Customer;

@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@ResponseBody
	@GetMapping("/")
	public ResponseEntity<String> home() {
		return new ResponseEntity<>("HOME", HttpStatus.OK);
	}
	
	
	
	public ModelAndView downloadExcel() {
		ModelAndView mav = new ModelAndView("");
		mav.addObject("list",createCustomers());
		return mav;
	}
	
	public List<Customer> createCustomers () {
		final int size = 10;
		List<Customer> customers = new ArrayList<>(size);
		
		IntStream.range(0, size).forEach(i -> {
			Customer c = new Customer();
			c.setSeq(i);
			c.setName("customer" + i);
			c.setEmail("customer" + i + "@github.com");
			String phone = "010-";
			for(int j=0;j<3;j++) {
				phone +=i;
			}
			phone += "-";
			for(int j=0;j<4;j++) {
				phone +=i;
			}
			c.setCellphone(phone);
			customers.add(c);
		});
		
		
		return customers;		
	}
	
	
	
	
	

}
