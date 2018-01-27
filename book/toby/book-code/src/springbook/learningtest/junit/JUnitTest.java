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
		
		// ��ó�� ���� ����� ù ��° �Ķ���Ϳ� Boolean Ÿ���� �����
		// ������ ���ǹ��� �ְ�, �� ����� is() ��ó�� �Ἥ true�� ��
		assertThat(contextObject == null || contextObject == this.context, is(true) );
		contextObject = this.context;
	}

	@Test
	public void test2() {
		assertThat( testObjects, not(hasItem(this)) ) ;
		testObjects.add(this);
		
		// ���ǹ��� �޾Ƽ� �� ����� true���� false ������ Ȯ���ϵ��� �������
		// assertTrue() ��� ������ �޼ҵ带 assertThat() ��� ���
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
		// 1) �빮�� �ΰ��
		// 2) �빮�� X
		// 2-1 ) �ҹ���
		// 2-2 ) Ư������
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


















