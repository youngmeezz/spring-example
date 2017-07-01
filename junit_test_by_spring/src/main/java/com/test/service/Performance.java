package com.test.service;

public class Performance {	
	public void perform() throws Exception {
		System.out.println("performed!!!");
		// throw NumberFormatException
		String s = "aa";
		Integer.parseInt(s);		
	}
}
