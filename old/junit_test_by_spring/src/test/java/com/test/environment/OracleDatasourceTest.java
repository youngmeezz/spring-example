package com.test.environment;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={
					"file:src/main/webapp/WEB-INF/spring/root-context.xml"
					,"file:src/main/webapp/WEB-INF/spring/persistence-context.xml"
					})
@Profile("oracle")
public class OracleDatasourceTest {
	@Autowired DataSource dataSource;
	
	@Test
	public void dataSourceTest() {
		System.out.println( dataSource.toString() );
	}

}
