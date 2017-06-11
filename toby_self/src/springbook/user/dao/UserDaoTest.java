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
// 테스트 메소드에서 애플리케이션 컨텍스트의 구성이나
// 상태를 변경하는 거승ㄹ 테스트 컨텍스트 프레임 워크에 알려줌
@DirtiesContext 
public class UserDaoTest {
	@Autowired
	private UserDao dao;
	
	private User user1;
	private User user2;
	private User user3;
		
	@Before
	public void setUp() {		
		user1 = new User("gyumee","박성철","springno1");
		user2 = new User("leegw700","이길원","springno2");
		user3 = new User("bumjin","박범진","springno3");
		
		// 테스트에서 UserDao가 사용할 DataSource
		// 오브젝트를 직접 생성		
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
		
		// 첫 번째 User의 id로 dao.get()을 실행하면, 첫 번쨰
		// User의 값을 가진 오브젝트를 돌려주는 지 확인
		User userget1 = dao.get(user1.getId());
		assertThat(userget1.getName(), is(user1.getName()));
		assertThat(userget1.getPassword(), is(user1.getPassword()));
		
		// 두번 째 User에 대해서도 같은 테스트 실행
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
	
	//테스트 중에 발생할 것으로 기대하는 예외 클래스를 지정
	@Test(expected=EmptyResultDataAccessException.class)
	public void getUserFailure() throws SQLException {
		dao.deleteAll();
		assertThat( dao.getCount(), is(0) );
		
		// 이 메소드 실행 중에 예외가 발생해야 함
		// 예외가 발생하지 않으면 테스트가 실패
		dao.get("unkown_id");		
	}
	
	public static void main(String[] args) {
		JUnitCore.main("springbook.user.dao.UserDaoTest");
	}
	
}
