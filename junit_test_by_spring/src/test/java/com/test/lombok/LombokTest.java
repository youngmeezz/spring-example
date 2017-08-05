package com.test.lombok;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class LombokTest {
	@Test
	public void lombok() {
		SampleLombokDomain domain = new SampleLombokDomain("Sample Name",12);
		
		domain.setName("Sample Name");
		domain.setAge(11);
				
		assertThat(domain.getName(),is("Sample Name"));
		assertThat(domain.getAge(), is(11));
		
		domain.testLog();
	}

}

