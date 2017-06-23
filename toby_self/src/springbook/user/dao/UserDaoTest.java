package springbook.user.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import springbook.user.domain.User;

public class UserDaoTest {
	private UserDao dao;
	
	private User user1;
	private User user2;
	private User user3;
		
	@Before
	public void setUp() {		
		user1 = new User("gyumee","�ڼ�ö","springno1");
		user2 = new User("leegw700","�̱��","springno2");
		user3 = new User("bumjin","�ڹ���","springno3");
		
		dao = new UserDao();		
		DataSource dataSource = new SingleConnectionDataSource (
				"jdbc:mysql://localhost/testdb","spring","book",true );
		dao.setDataSource(dataSource);
	}
	
	@Test
	public void getAll() throws SQLException {
		dao.deleteAll();
		
		List<User> users0 = dao.getAll();
		assertThat(users0.size(), is(0));
		
		dao.add(user1); // id : gyumee
		List<User> users1 = dao.getAll();
		assertThat(users1.size(), is(1));
		checkSameUser(user1,users1.get(0));
		
		dao.add(user2); // id : leegw700
		List<User> users2 = dao.getAll();
		assertThat(users2.size(), is(2));
		checkSameUser(user1, users2.get(0));
		checkSameUser(user2, users2.get(1));
		
		dao.add(user3); // id : bumjin
		List<User> users3 = dao.getAll();
		assertThat(users3.size(), is(3));
		
		checkSameUser(user3, users3.get(0));
		checkSameUser(user1, users3.get(1));
		checkSameUser(user2, users3.get(2));		
	}
	
	private void checkSameUser(User user1, User user2) {
		assertThat(user1.getId(), is(user2.getId()));
		assertThat(user1.getName(), is(user2.getName()));
		assertThat(user1.getPassword(), is(user2.getPassword()));
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

