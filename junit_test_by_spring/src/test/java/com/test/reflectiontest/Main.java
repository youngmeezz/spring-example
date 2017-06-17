package com.test.reflectiontest;

import static org.hamcrest.CoreMatchers.is;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 리플렉션 이것 저것 테스트
 * @author zaccoding
 *
 */
public class Main {
	static Logger logger = LoggerFactory.getLogger(Main.class);
	static Map<String,String> domainMap;
	
	@BeforeClass
	public static void setUp() {
		domainMap = new HashMap<>();
		domainMap.put("driver", "mydriver");
		domainMap.put("url", "myurl");
		domainMap.put("username", "myusername");
		domainMap.put("password", "mypassword");
	}
	
	@Test
	public void invokeTest() throws Exception {
		TestDomain vo = getObjectFromMap2(com.test.reflectiontest.TestDomain.class);
		
		Assert.assertThat( vo.getDriver(), is(domainMap.get("driver")));
		Assert.assertThat( vo.getUrl(), is(domainMap.get("url")));
		Assert.assertThat( vo.getUsername(), is(domainMap.get("username")));
		Assert.assertThat( vo.getPassword(), is(domainMap.get("password")));
	}
	
	public <T extends Object> T getObjectFromMap(Class<T> clazz) throws Exception {
		Object retObj = clazz.newInstance();
		Iterator<String> keyItr = domainMap.keySet().iterator();		
		while( keyItr.hasNext() ) {
			String key = keyItr.next();
			String value = domainMap.get( key );
			logger.info("key : " + key + ", value : " + value);			
			Method method = clazz.getMethod( getSetterFromFieldName(key) );
			logger.info( method.getName() );
			method.invoke(key, value);
		}
		return clazz.cast(retObj);
	}
	
	public <T extends Object> T getObjectFromMap2(Class<T> clazz) throws Exception {
		Object retObj = clazz.newInstance();
		
		Method[] methods = clazz.getMethods();
		for( Method method : methods ) {
			String fieldName = getFiledNameFromSetter( method.getName() );
			if( fieldName == null ) continue;
			
			String value = domainMap.get( fieldName );
			if( value == null ) continue;
			
			method.invoke( retObj, value);
		}
		return clazz.cast(retObj);
	}
		
	public String getSetterFromFieldName(String fieldName) {
		StringBuilder sb = new StringBuilder(fieldName.length() + 3);
		return sb.append("set")
				 .append(toUpperChar(fieldName.charAt(0)))
				 .append( fieldName.substring(1) )
				 .toString();
	}
	
	private String getFiledNameFromSetter(String methodName) {
		if( methodName.startsWith("set") ) {
			return new StringBuilder().append(toLowerChar(methodName.charAt(3)))
					.append(methodName.substring(4))
					.toString();
		} else {
			return null;
		}
	}
	
	private char toUpperChar(char ch) {
		if( ch >= 'a' && ch <= 'z')
			return (char)(ch + 'A' - 'a');
		return ch;
	}
	
	private char toLowerChar(char ch) {
		if( ch >= 'A' && ch <= 'Z')
			return (char)(ch - 'A' + 'a');
		return ch;
	}
}
