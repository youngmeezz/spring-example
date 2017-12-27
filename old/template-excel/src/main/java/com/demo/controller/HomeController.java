package com.demo.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.demo.domain.Customer;
import com.demo.domain.Order;
import com.demo.domain.Product;
import com.demo.domain.ProductDetail;

import lombok.extern.java.Log;

@Controller
@Log
public class HomeController {
	private List<Order> orders = new ArrayList<>();
	
	@GetMapping("/")
	public String home() {
		log.info("## request home");
		return "main";
	}
	
	@PostMapping("/excel-down/test")
	//@ResponseBody
	public String downloadExcel2(Model model, @RequestBody String type) {
		log.info("## [request /excel-down/test] type : " + type);		
		model.addAttribute("datas", createOrders());
		return "xlsView";
	}
	
	
	@GetMapping(value="/excel-down/{type}")
	public String downloadExcel(Model model, @PathVariable("type") String type) {	    
		// model.addAttribute("datas", customers);
	    
	    model.addAttribute("datas", createOrders());
		//model.addAttribute("datas", Arrays.asList(createOrders().get(0)));
		
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
		
		log.info("## [request excel-down] orders size : " + orders.size());
		
		return viewName;
	}
	
	public List<Order> createOrders() {
	    if(orders.size() != 0) {
	        return orders;
	    }
	    
	    Calendar cal = Calendar.getInstance();
        IntStream.range(1, 11).forEach(i -> {
            Order order = new Order();
            order.setOrderNumber(i);
            order.setOrderDate(new Date());            
            order.setCustomer(createCustomer(i, cal));
            order.setProduct(createProduct(i));            
            orders.add(order);
        });	    
	    
	    return orders;
	}
	
	private Customer createCustomer(int i, Calendar cal) {
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
        
        return c;
    }
    
    private Product createProduct(int i) {
        Product p = new Product();
        p.setPName("product" + i);
        p.setPno((long)i);
        p.setPrice(2000);
        
        // p.setDetail(createProductDetail());
        
        return p;
    }
    
    private ProductDetail createProductDetail() {
        ProductDetail p = new ProductDetail();
        p.setCountry("country");
        p.setManufacture("manufacture");
        
        return p;
    }
}
