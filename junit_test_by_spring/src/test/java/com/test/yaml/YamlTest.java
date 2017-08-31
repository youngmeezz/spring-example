package com.test.yaml;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

/**
 * API 
 * : https://bitbucket.org/asomov/snakeyaml/wiki/Documentation
 * 
 * @author zaccoding
 * @Date 2017. 8. 10.
 */

public class YamlTest {
	@Test
	public void loadFromString() {
		Yaml yaml = new Yaml();
		String document = "- Value1\n- Value2\n- Value3";
		
		List<String> list = (List<String>)yaml.load(document);
		assertTrue(list.size() == 3);
		
		assertThat(list.get(0),is("Value1"));
		assertThat(list.get(1),is("Value2"));
		assertThat(list.get(2),is("Value3"));
	}
	
	

}
