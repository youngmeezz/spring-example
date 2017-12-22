package springbook.learningtest.junit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.matchers.JUnitMatchers.either;
import static org.junit.matchers.JUnitMatchers.hasItem;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="junit.xml")
public class JUnitTest {
	@Autowired
	ApplicationContext context;
	
	static Set<JUnitTest> testObjects = new HashSet<JUnitTest>();
	static ApplicationContext contextObject = null;
	
	@Test
	public void test1() {
		assertThat( testObjects, not(hasItem(this)) ) ;
		testObjects.add(this);
		
		// 메처와 비교할 대상인 첫 번째 파라미터에 Boolean 타입의 결과가
		// 나오는 조건문을 넣고, 그 결과를 is() 메처를 써서 true와 비교
		assertThat(contextObject == null || contextObject == this.context, is(true) );
		contextObject = this.context;
	}

	@Test
	public void test2() {
		assertThat( testObjects, not(hasItem(this)) ) ;
		testObjects.add(this);
		
		// 조건문을 받아서 그 결과가 true인지 false 인지를 확인하도록 만들어진
		// assertTrue() 라는 검증용 메소드를 assertThat() 대신 사용
		assertTrue(contextObject == null || contextObject == this.context);
		contextObject = this.context;
	}
	
	@Test
	public void test3() {
		assertThat( testObjects, not(hasItem(this)) ) ;
		testObjects.add(this);
		
		assertThat( contextObject, either(is(nullValue())).or(is(this.context)));		
	}
	
//	@Test
//	public void assertTest() {
//		String test = null;
//		Assert.notNull(test,"test must be not null");
//	}
	
	@Test
	public void lowCaseTest() {
		// 1) 대문자 인경우
		// 2) 대문자 X
		// 2-1 ) 소문자
		// 2-2 ) 특수문자
		assertTrue( 'a' == toLowerCase('A'));
		assertThat('a',is('a'));
		assertThat('!',is('!'));
	}
	
	private char toLowerCase(char ch) {
		if(ch >= 'A' && ch <= 'Z') {
			return (char)(ch + 'a' - 'A');
		}
		return ch;
	}
}


















