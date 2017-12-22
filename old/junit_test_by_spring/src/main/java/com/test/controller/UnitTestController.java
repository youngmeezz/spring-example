package com.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.service.UnitTestService;

@RequestMapping(value="/unit-test")
public class UnitTestController {
    @Autowired
    public UnitTestService unitTestService;
    
    @RequestMapping("/test")
    public String test() {
        System.out.println("## [com.test.controller.UnitTestController::test() request]");
        return "test" + unitTestService.testMethod();        
    }
    
    @RequestMapping("/test-http")
    public ResponseEntity<String> handleRequest (RequestEntity<String> requestEntity) {
        System.out.println("request body : " + requestEntity.getBody());
        HttpHeaders headers = requestEntity.getHeaders();
        System.out.println("request headers : " + headers);
        HttpMethod method = requestEntity.getMethod();
        System.out.println("request method : " + method);
        System.out.println("request url: " + requestEntity.getUrl());

        ResponseEntity<String> responseEntity = new ResponseEntity<>("my response body",
                                                                     HttpStatus.OK);
        return responseEntity;
    } 
    
}
