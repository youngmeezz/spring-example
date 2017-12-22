package com.test.persistence;
//package com.test.persistence;
//
//import static org.hamcrest.CoreMatchers.is;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import com.test.domain.TestDomain;
//import com.test.persistence.TestMapper;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
//public class TestMapperTest {
//	private static Logger logger = LoggerFactory.getLogger(TestMapperTest.class);
//	
//	private TestDomain vo1;
//	private TestDomain vo2;
//	private TestDomain vo3;
//	
//	@Autowired
//	TestMapper testMapper;
//	
//	@Before
//	public void setUp() {		
//		vo1 = new TestDomain();
//		vo1.setName("test1");
//		vo2 = new TestDomain();
//		vo2.setName("test2");
//		vo3 = new TestDomain();
//		vo3.setName("test3");
//	}
//	
//	@Test
//	public void saveAndFindTest() {
//		testMapper.deleteAll();
//		
//		testMapper.save( vo1.getName() );
//		TestDomain findByNameVO = testMapper.findOneByName( vo1.getName() );
//		TestDomain findByIdVO = testMapper.findOneById(findByNameVO.getId());
//		Assert.assertThat( vo1.getName(), is(findByNameVO.getName() ));
//		Assert.assertThat( findByNameVO.getName() , is(findByIdVO.getName() ));
//		
//		testMapper.save( vo2.getName() );
//		findByNameVO = testMapper.findOneByName( vo2.getName() );
//		findByIdVO = testMapper.findOneById(findByNameVO.getId());
//		Assert.assertThat( vo2.getName(), is(findByNameVO.getName() ));
//		Assert.assertThat( findByNameVO.getName() , is(findByIdVO.getName() ));
//		
//		testMapper.save( vo3.getName() );
//		findByNameVO = testMapper.findOneByName( vo3.getName() );
//		findByIdVO = testMapper.findOneById(findByNameVO.getId());
//		Assert.assertThat( vo3.getName(), is(findByNameVO.getName() ));
//		Assert.assertThat( findByNameVO.getName() , is(findByIdVO.getName() ));
//		
//		findByNameVO = testMapper.findOneByName(" test ");
//		Assert.assertNull(findByNameVO);
//	}
//	
//
//}
