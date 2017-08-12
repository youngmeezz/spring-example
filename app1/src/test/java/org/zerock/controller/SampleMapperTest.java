package org.zerock.controller;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.zerock.persistence.SampleMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
	locations ={"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class SampleMapperTest {
	
	@Inject
	private SampleMapper mapper;
	
//	@Test
//	public void testTime() {
//		System.out.println(mapper.getClass().getName());
//		System.out.println(mapper.getTime());
//	}	
	
//	@Test
//	public void testName() {
//		String name = mapper.getEmail("hiva1","1234");
//		System.out.println(name);
//	}
	
	@Test
	public void testName2() {
		String name = mapper.getUserName("hiva1","1234");
		System.out.println(name);
	}
	
}
