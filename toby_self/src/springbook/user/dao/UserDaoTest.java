package springbook.user.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import springbook.user.domain.User;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations="/applicationContext.xml")
// �׽�Ʈ �޼ҵ忡�� ���ø����̼� ���ؽ�Ʈ�� �����̳�
// ���¸� �����ϴ� �Ž¤� �׽�Ʈ ���ؽ�Ʈ ������ ��ũ�� �˷���
@DirtiesContext 
public class UserDaoTest {
	@Autowired
	private UserDao dao;
	
	private User user1;
	private User user2;
	private User user3;
		
	@Before
	public void setUp() {		
		user1 = new User("gyumee","�ڼ�ö","springno1");
		user2 = new User("leegw700","�̱��","springno2");
		user3 = new User("bumjin","�ڹ���","springno3");
		
		// �׽�Ʈ���� UserDao�� ����� DataSource
		// ������Ʈ�� ���� ����		
		DataSource dataSource = new SingleConnectionDataSource (
				"jdbc:mysql://localhost/testdb","spring","book",true );
		dao.setDataSource(dataSource);
	}
	
	@Test 
	public void andAndGet() throws SQLException {
		dao.deleteAll();
		assertThat( dao.getCount(), is(0) );
				
		dao.add(user1);
		dao.add(user2);
		assertThat( dao.getCount(), is(2) );
		
		// ù ��° User�� id�� dao.get()�� �����ϸ�, ù ����
		// User�� ���� ���� ������Ʈ�� �����ִ� �� Ȯ��
		User userget1 = dao.get(user1.getId());
		assertThat(userget1.getName(), is(user1.getName()));
		assertThat(userget1.getPassword(), is(user1.getPassword()));
		
		// �ι� ° User�� ���ؼ��� ���� �׽�Ʈ ����
		User userget2 = dao.get(user2.getId());
		assertThat(userget2.getName(), is(user2.getName()));
		assertThat(userget2.getPassword(), is(user2.getPassword()));
	}
	
	@Test
	public void count() throws SQLException {		
		dao.deleteAll();
		assertThat( dao.getCount(), is(0) );
		
		dao.add(user1);
		assertThat( dao.getCount(), is(1) );
		
		dao.add(user2);
		assertThat( dao.getCount(), is(2) );
		
		dao.add(user3);
		assertThat( dao.getCount(), is(3) );		
	}
	
	//�׽�Ʈ �߿� �߻��� ������ ����ϴ� ���� Ŭ������ ����
	@Test(expected=EmptyResultDataAccessException.class)
	public void getUserFailure() throws SQLException {
		dao.deleteAll();
		assertThat( dao.getCount(), is(0) );
		
		// �� �޼ҵ� ���� �߿� ���ܰ� �߻��ؾ� ��
		// ���ܰ� �߻����� ������ �׽�Ʈ�� ����
		dao.get("unkown_id");		
	}
	
	public static void main(String[] args) {
		JUnitCore.main("springbook.user.dao.UserDaoTest");
	}
	
}
