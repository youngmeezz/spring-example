package com.test.reflectiontest;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Test;

public class BasicApiTest {
	/**
	 * 기본 reflection 연습
	 */
	//@Test
	public void basicTest() {
		Class c = TestDomain.class;
		Method[] methods = c.getMethods();
		Field[] fields = c.getFields();
		Constructor[] constructors = c.getConstructors();
		Field[] declaredFields = c.getDeclaredFields();
		
		System.out.println("-------- Methods ------");
		for( Method m : methods ) {
			System.out.println( m.getName() );
		}
		System.out.println("----------------");
		
		System.out.println("-------- Fields --------");
		for( Field f : fields ) {			
			System.out.println(f.getName());			
		}
		System.out.println("----------------");
		
		System.out.println("-------- Constructors --------");
		for( Constructor cs : constructors ) {
			System.out.println(cs.getName() );
		}
		System.out.println("----------------");
		
		System.out.println("-------- Declared Fields --------");
		for( Field f : declaredFields ) {			
			System.out.println(f.getName());			
		}
		System.out.println("----------------");		
	}
	
	@Test
	public void accessField() throws Exception {
		Class c = TestDomain.class;
		Field f = c.getDeclaredField("driver");
		System.out.println(f.getType());
	}
	
	@Test
	public void parameterTest() throws Exception {
		Class c = TestDomain.class;
		Method[] methods = c.getMethods();
		for( Method method : methods ) {
			Class<?>[] clazzArr = method.getParameterTypes();
			for( Class cc: clazzArr) {
				System.out.println(cc.getName() );
			}
		}		
	}
	

}
