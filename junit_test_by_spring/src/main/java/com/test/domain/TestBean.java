package com.test.domain;

public class TestBean {
	private String name;
	public void init() {
		name = "testBean";
		System.out.println("TestBean.init()");
	}
	public String getName() {
		return name;
	}	
}

