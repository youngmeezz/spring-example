package com.test.reflectiontest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class InvokeTest {
	@Test
	public void test() throws Exception {
		InvokeDomain domain = new InvokeDomain();
		assertThat(domain.execute("invoke1"),is("invoke1"));
		assertThat(domain.execute("invoke2"),is("invoke2"));
	}
}
