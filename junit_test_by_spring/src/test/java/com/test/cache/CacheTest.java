package com.test.cache;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.test.domain.TestDomain;
import com.test.persistence.TestDao;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"})	
public class CacheTest {
	private static Logger logger = LoggerFactory.getLogger(CacheTest.class);
	
	@Inject TestDao testDao;
	@Inject CacheManager cacheManager;
	
	@Test
	public void cacheManagerTest() {
		assertNotNull(cacheManager);
	}
	
	@Test
	public void selectCacheTest() {
		testDao.deleteAll();
		
		String name = "test";		
		testDao.save(name);
		
		for(int i=0; i<10;i++ ) {
			TestDomain vo = testDao.findOneByName(name);
			assertTrue( vo.getName() == name);
		}
		
	}
}
