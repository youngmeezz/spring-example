package com.demo.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.demo.domain.Customer;

import lombok.extern.java.Log;

@Controller
@Log
public class HomeController {
	private List<Customer> customers = createCustomers();
	
	@GetMapping("/")
	public String home() {
		log.info("## request home");
		return "main";
	}
	
	@GetMapping(value="/excel-down/{type}")
	public String downloadExcel(Model model, @PathVariable("type") String type) {
		if(customers == null || customers.size() == 0) {
			customers = createCustomers();
		}		
		model.addAttribute("customers", customers);
		
		String viewName = null;
		if("xlsx".equals(type)) {
			viewName = "xlsxView";
		}
		else if("xlsxStream".equals(type)) {
			viewName = "xlsxStreamView";
		}
		else if("xlsView-basic".equals(type)) {
			viewName = "xlsView-basic";
		}
		else {
			viewName = "xlsView";
		}		
		
		log.info("## [request excel-down] customers size : " + customers.size());
		
		return viewName;
	}
	
	public List<Customer> createCustomers () {
		final int size = 10;
		List<Customer> customers = new ArrayList<>(size);
		Calendar cal = Calendar.getInstance();
		
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
			
			cal.set(Calendar.MONTH, i%12);
			
			c.setRegDate(cal.getTime());						
			customers.add(c);
		});
		
		
		return customers;		
	}
	
	
	
	
	

}
