package com.test.service;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class UnitTestService {
    @PostConstruct
    public void setUp() {
        System.out.println("## [after created UnitTestService instance]");
    }
    
    public int testMethod() {
        return 1;
    }
    

}
