package com.test.reflectiontest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InvokeDomain {	
	public String invoke1() {
		return "invoke1";
	}
	
	public String invoke2() {
		return "invoke2";
	}
	
	public String execute(String methodName) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> clazz = this.getClass();
		Method method = null;		
		if(methodName.equals("invoke1")) {			
			method = clazz.getMethod("invoke1");
			return (String)method.invoke(this);			
		} else if(methodName.equals("invoke2")) {
			method = clazz.getMethod("invoke2");
			return (String)method.invoke(this);
		} else {
			return "not exist method name";
		}
	}
}
