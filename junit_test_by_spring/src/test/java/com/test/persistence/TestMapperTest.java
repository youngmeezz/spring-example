package com.test.persistence;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.test.domain.TestDomain;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class TestMapperTest {
	private static Logger logger = LoggerFactory.getLogger(TestMapperTest.class);
	
	private static TestDomain vo1;
	private static TestDomain vo2;
	private static TestDomain vo3;
	
	@Autowired TestMapper testMapper;
	
	@BeforeClass
	public static void setUp() {	
		logger.info("setUp()..");
		vo1 = new TestDomain();
		vo1.setName("test1");
		vo2 = new TestDomain();
		vo2.setName("test2");
		vo3 = new TestDomain();
		vo3.setName("test3");
	}
	
	@Test
	public void deleteAndInitTest() {
		testMapper.deleteAll();
		testMapper.dropSequence();
		testMapper.createSequence();
		
		testMapper.save( vo1.getName() );
		List<TestDomain> list = testMapper.findAllByName(vo1.getName());
		assertTrue( list.size() == 1);
		assertTrue( list.get(0).getId() == 1);		
	}
}
