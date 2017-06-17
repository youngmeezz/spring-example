package com.test.cache;

import static org.hamcrest.CoreMatchers.is;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired TestMapper testMapper;
	
	@Test
	public void selectCacheTest() {
		testMapper.deleteAll();
		
		String name = "test";		
		testMapper.save(name);
		TestDomain addedVO = testMapper.findOneByName(name);
		
		String value = testMapper.findNameById( addedVO.getId() );		
		for( int i=0; i<100; i++ ){
			String cacheValue = testMapper.findNameById( addedVO.getId() );
			if( value != cacheValue ) {
				logger.info("is not cache");
			}
		}
	}
}
