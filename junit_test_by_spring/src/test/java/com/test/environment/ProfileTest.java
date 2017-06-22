package com.test.environment;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.test.domain.EnvProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@ActiveProfiles("dev")
public class ProfileTest {
	@Autowired DataSource dataSource;
	@Autowired EnvProfile envProfile;	
	
	@Test
	public void environmentsTest() {
		assertThat( envProfile.getEnvironmentProfile(), is("dev"));		
	}
	
	@Test
	public void defaultProfile() {
		assertNotNull(dataSource);
	}

}
