package com.test.configtest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;

public class PropertiesTest {	
	static Properties prop;
	static Map<String,String> defaultMap = new HashMap<>();	
	
	@BeforeClass
	public static void setUp() {
		if( prop == null )
			initProperties();
		defaultMap.put("key1", "value1");
		defaultMap.put("key2", "value2");
		defaultMap.put("key3", "value3");
		defaultMap.put("key5", "value5");
	}
	
	@Test
	public void loadTest() {
		assertNotNull(prop);
		Iterator<Object> keyItr = prop.keySet().iterator();
		while( keyItr.hasNext() ) {
			String key = (String)keyItr.next();
			assertThat( defaultMap.get(key), is(prop.get(key)) );
		}
		assertThat(prop.getProperty("key4",""), is("") );
	}
	
	private static void initProperties() {
		prop = new Properties();
		try ( FileReader fis = new FileReader(new File( "src/main/resources/test.properties" )) ) {
			prop.load(fis);			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	

}
