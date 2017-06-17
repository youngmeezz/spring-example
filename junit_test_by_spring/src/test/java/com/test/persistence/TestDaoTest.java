package com.test.persistence;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;

import javax.inject.Inject;

import org.junit.Assert;
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
		vo1 = new TestDomain();
		vo1.setName("test1");
		vo2 = new TestDomain();
		vo2.setName("test2");
		vo3 = new TestDomain();
		vo3.setName("test3");
	}
	
	@Test
	public void saveAndFindTest() {
		testDao.deleteAll();
		
		testDao.save( vo1.getName() );
		TestDomain findByNameVO = testDao.findOneByName( vo1.getName() );
		TestDomain findByIdVO = testDao.findOneById(findByNameVO.getId());
		Assert.assertThat( vo1.getName(), is(findByNameVO.getName() ));
		Assert.assertThat( findByNameVO.getName() , is(findByIdVO.getName() ));
		
		testDao.save( vo2.getName() );
		findByNameVO = testDao.findOneByName( vo2.getName() );
		findByIdVO = testDao.findOneById(findByNameVO.getId());
		Assert.assertThat( vo2.getName(), is(findByNameVO.getName() ));
		Assert.assertThat( findByNameVO.getName() , is(findByIdVO.getName() ));
		
		testDao.save( vo3.getName() );
		findByNameVO = testDao.findOneByName( vo3.getName() );
		findByIdVO = testDao.findOneById(findByNameVO.getId());
		Assert.assertThat( vo3.getName(), is(findByNameVO.getName() ));
		Assert.assertThat( findByNameVO.getName() , is(findByIdVO.getName() ));
		
		findByNameVO = testDao.findOneByName(" test ");
		Assert.assertNull(findByNameVO);
	}
}