package com.test.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.test.constant.Order;

public class StringUtilsTest {
	private String defaultAscValue;
	private String defaultDescValue;
	
	@Before 
	public void setUp() {
		defaultAscValue = "1,3,5";
		defaultDescValue = "5,3,1";
	}

	@Test 
	public void getString() {
		//given
		String[] unSortedValues = new String[] { "1,3,5", "1,5,3", "3,1,5", "3,5,1", "5,1,3", "5,3,1" };
		for (String s : unSortedValues) {
			//when
			String ascSorted = StringUtils.getSortedStringWithDelimeter(s, ",", Order.ASC);
			String descSorted = StringUtils.getSortedStringWithDelimeter(s, ",", Order.DESC);
			//than
			assertThat(ascSorted, is(defaultAscValue));
			assertThat(descSorted, is(defaultDescValue));
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void exception() {
		// given
		String notNumberFormat = "1,5,a";
		// when
		StringUtils.getSortedStringWithDelimeter(notNumberFormat, ",", Order.ASC);
	}
}
