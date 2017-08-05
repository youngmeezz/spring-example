package com.test.reflectiontest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;

@JsonIgnoreProperties({"ignore"})
class Person {
	String name;
	int age;
	List<String> hobbies;
	private String ignore;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public List<String> getHobbies() {
		return hobbies;
	}
	public void setHobbies(List<String> hobbies) {
		this.hobbies = hobbies;
	}
	public String getIgnore() {
		return ignore;
	}
	public void setIgnore(String ignore) {
		this.ignore = ignore;
	}
}

public class EqualsReflector {
	
	@Test
	public void compare() {
		// when
		Person person1 = new Person();
		person1.setAge(10);
		person1.setName("person");
		person1.setHobbies(Arrays.asList("h1,h2"));
		
		Person person2 = new Person();
		person2.setAge(10);
		person2.setName("person");
		person2.setHobbies(Arrays.asList("h1,h2"));
		
		// then
		// compare(person1,person2,Person.class);
		Gson gson = new Gson();
		System.out.println(gson.toJson(person1).equals(gson.toJson(person2)));
	}
	
	private <T> void compare(T o1, T o2, Class<T> clazz) {
		Method[] methods = clazz.getMethods();
		if(methods == null)
			return;
		JsonIgnoreProperties ignores = clazz.getAnnotation(JsonIgnoreProperties.class);
		System.out.println(Arrays.toString(ignores.value()));
		
		try {
			for(Method method : methods) {
				if(!method.getName().startsWith("get") || method.getName().equals("getClass")) 
					continue;
				
				System.out.println("invoke : " + method.getName());
								
				System.out.println(method.invoke(o1));
				System.out.println(method.invoke(o2));
				System.out.println(method.invoke(o1).equals(method.invoke(o2)));
				
//				System.out.println("## check method : " + method.getName());
//				Class<?> retType = method.getReturnType();
//				if( retType.isPrimitive() ) {
//					System.out.println("is primitive : " + retType);
//				}
//				else {
//					System.out.println("is not primitive : " + retType);
//				}							
			}			
		}
		catch(IllegalAccessException |IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
		
	@Test
	@Ignore
	public void test() {
		Method[] methods = Person.class.getDeclaredMethods();
		if(methods == null) {
			return;			
		}
		
		for(Method method : methods) {
			if(method.getName().startsWith("get")) {
				System.out.println(method.getName());
			}
		}
	}

}
