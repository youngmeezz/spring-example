package com.test.persistence;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.test.domain.TestDomain;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class TestDaoTest {
	private static Logger logger = LoggerFactory.getLogger(TestDaoTest.class);
	
	private TestDomain vo1;
	private TestDomain vo2;
	private TestDomain vo3;
	
	@Inject
	TestDao testDao;
	
	@Before
	public void setUp() {
		logger.info("TestDaoTest setUp()");
		vo1 = new TestDomain();
		vo1.setName("test1");
		vo2 = new TestDomain();
		vo2.setName("test2");
		vo3 = new TestDomain();
		vo3.setName("test3");
	}
	
	@Test
	public void deleteAllAndInitSeqTest() {
		
	}
	
	@Test
	public void saveAndFindTest() {
		testDao.deleteAll();
		
		testDao.save( vo1.getName() );
		TestDomain last1 = testDao.findOneLastSaved();
		System.out.println(last1);		
	}
}







