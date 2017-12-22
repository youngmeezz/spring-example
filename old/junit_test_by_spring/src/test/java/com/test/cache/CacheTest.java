package com.test.cache;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.test.domain.TestDomain;
import com.test.persistence.TestMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"})	
public class CacheTest {
	private static Logger logger = LoggerFactory.getLogger(CacheTest.class);
	private static List<TestDomain> testList;
	private static String SAME_NAME;
	
	@Inject TestMapper testMapper;
	@Inject CacheManager cacheManager;
	
	@BeforeClass 
	public static void setUp() {
		logger.info("setUp()");
		testList = new ArrayList<>();
		SAME_NAME = "test";
		for(int i=1; i<10;i++ ) {
			TestDomain vo = new TestDomain();
			vo.setName(SAME_NAME);	
			testList.add(vo);
		}		
	}
	
	@Test
	public void selectCacheTest() {
		testMapper.deleteAll();
		
		// insert
		for(TestDomain vo : testList ) {
			testMapper.save( vo.getName() );
		}		
		
		
		// create cache
		List<TestDomain> list1 = testMapper.findAllByName(SAME_NAME);
		// using cache
		List<TestDomain> list2 = testMapper.findAllByName(SAME_NAME);
		// equal
		assertTrue(isEqualsList(list1,list2));
		
		// remove cache
		testMapper.save("test");
		
		// create cache
		List<TestDomain> list3 = testMapper.findAllByName(SAME_NAME);
		// not equals
		assertFalse(isEqualsList(list2,list3));
		
		// using cache
		List<TestDomain> list4 = testMapper.findAllByName(SAME_NAME);
		assertTrue(isEqualsList(list3,list4));
		
		// not using cache
		testMapper.findAllByName("test2");
	}
	
	private boolean isEqualsList(List<TestDomain> list1 , List<TestDomain> list2) {
		for(TestDomain vo1 : list1 ) {
			boolean existEqualDomain = false;
			
			for(TestDomain vo2 : list2 ) {
				if( vo1 == vo2 ) {
					existEqualDomain = true;
					break;
				}
			}
			
			if( !existEqualDomain ) {
				return false;
			}
		}
		return true;
	}
}









