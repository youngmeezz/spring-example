# ch2 테스트  

- <a href="#2.1">UserDaoTest 다시 보기 </a>
- <a href="#2.2">UserDaoTest 개선</a>
- <a href="#2.3">개발자를 위한 테스팅 프레임워크</a>
- <a href="#2.4">스프링 테스트 적용</a>
- <a href="#2.5">학습 테스트로 배우는 스프링</a>
- <a href="#2.6">정리</a>

<div id="2.1"></div>

## Intro

스프링이 개발자에게 제공하는 가장 중요한 가치 는 *객체지향*과 *테스트*

=> 애플리케이션은 계속 변하고 복잡하는 변화해감

- 첫번쨰 전략 : 확장과 변화를 고려한 객체지향적 설계와 그것을 효과적으로 담아낼 수 있는
IoC/DI 같은 기술
- 두번째 전략 : 변화에 유연하게 대처할 수 있는 테스트 기술


## 2.1 UserDaoTest 다시 보기


### 2.1.1 테스트의 유용성

chap01에서 UserDao의 클래스를 책임에 따라 분리 & 인터페이스 도입

& 오브젝트 팩토리를 통해 생성하도록 만들기 & 스프링의 DI 방식을 XML 설정 파일 적용

=> 처음과 동일한 기능을 수행함을 보장하는 것 == 테스트



### 2.1.2 UserDaoTest의 특징

*기존 UserDaoTest*
<pre>
public class UserDaoTest {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");

		UserDao dao = context.getBean("userDao", UserDao.class);

		User user = new User();
		user.setId("whiteship");
		user.setName("백기선");
		user.setPassword("married");

		dao.add(user);

		System.out.println(user.getId() + " 등록 성공");

		User user2 = dao.get(user.getId());
		System.out.println(user2.getName());
		System.out.println(user2.getPassword());

		System.out.println(user2.getId() + " 조회 성공");
	}
}
</pre>


=>
- 자바에서 가장 손쉽게 실행 가능한 main() 메소드 이용
- 테스트할 대상인 UserDao의 오브젝트를 가져와 메소드를 호출
- 테스트에 사용할 입력 값(User Object)을 직접 코드에서 만듬
- 테스트의 결과를 콘솔에 출력
- 각 단계의 작업이 에러 없이 끝나면 콘솔에 성공 메시지로 출력



**웹을 통한 DAO 테스트 방법의 문제점**

보통 웹에서 서비스, MVC 프레젠테이션 계층까지 포함한 모든 입출력 기능을 대충이라도

만들고 테스트

=> 웹 화면을 통해 값을 입력하고, 기능을 수행하고 결과를 확인하는 방법은 가장 흔히 쓰이는 방법이지만,

DAO에 대한 테스트로서는 단점이 너무 많음 (+서비스,컨트롤러,뷰 등 모든 레이어의 기능을 다 만들고

테스트를 하는 것이 더 큰 문제)


**작은 단위의 테스트**

테스트하고자 하는 대상이 명확하다면, 그 대상에만 집중해서 테스트하는 것이 바람직함

UserDaoTest는 한 가지 관심에 집중할 수 있게 만든 작은 단위로 만들어진 테스트

=> 이렇게 작은 단위의 코드에 대해 테스트를 수행하는 것을 단위 테스트(unit test)

(단위는 절대적인 것이 아니라, 하나의 관심에 집중해서 효율적으로 테스트할 만한 범위의 단위라고 보면 됨)



*단위 테스트가 필요한 이유?*

개발자가 설계하고 만든 코드가 원래 의도한 대로 동작하는지를 개발자 스스로 빨리 확인받기 위해서!


**자동수행 테스트 코드**

UserDaoTest의 한 가지 특징은 테스트할 데이터가 코드를 통해 제공 되고, 테스트 작업 역시

코드를 통해 자동으로 실행 된다는 점 (웹 화면 띄우고 폼에 값 입력하는 것과 비교)

=> 이렇게 테스트는 자동으로 수행되도록 코드로 만들어지는 것이 중요

=> 또한, 애플리케이션을 구성하는 클래스 안에 테스트 코드를 포함시키는 것보다는

별도의 테스트용 클래스를 만들어서 테스트 코드를 넣는 편이 나음

( e.g : 처음 UserDao안에 main메소드 -> UserDaoTest )

=> 자동으로 수행되는 테스트는 자주 반복할 수 있음



**지속적인 개선과 점진적인 개발을 위한 테스트**

처음 초난감 DAO -> 객체지향적 코드로 발전시키면서 일등 공신은 테스트 !

만약 테스트를 하지 않았더라면, 미덥지 않을 수 있고 중간에 이쯤 수정하자 하고

포기할 수 있었음. 테스트를 통해 무엇이 잘못됐는지 바로 확인할 수 있었던 것

추가적으로, UserDao의 기능을 추가하려고 할 때도 미리 만들어둔 테스트 코드는

유용하게 쓰일 수 있음



### 2.1.3 UserDaoTest의 문제점

- **수동 확인 작업의 번거로움** <br>
UserDaoTest는 테스트를 수행하는 과정과 입력 데이터의 준비를 모두 자동으로 진행하도록 만듬 <br>
하지만 여전히 사람 눈으로 확인하는 과정이 필요 <br>
( add() 후 get() 한 값이 일치하는지 확인해주지 않고, 콘솔에 값만 출력 해줌 <br>
콘솔에 나온 값을 보고 눈으로 확인해야 함 ) <br>
=> 몇 가지 필드에 대해 테스트하는 건 별 수고가 아닐 수 있지만 <br>
검증해야 하는 양이 많고 복잡해지면 불편함을 느끼게 될 수 밖에없고 <br>
작은 차이는 미처 발견하지 못하고 넘어가는 실수를 할 가능성도 있음


- **실행 작업의 번거로움** <br>
아무리 간단히 실행 가능 한 main() 메소드라도 매번 실행하는 것은 제법 번거로움 <br>
DAO가 많아지면 main() 메소드도 그만 큼 만들어짐<br>
전체 기능을 테스트해보기 위해 main() 메소드를 수백 번 실행할 수고가 필요할 수 있음<br>
또한 그 결과를 눈으로 확인해서 기록 후 이를 종합해서 전체 기능을 모두 테스트한<br>
결과를 정리하려면 이것도 제법 큰 작업<br>
=> main() 메소드를 사용하는 것보다, 좀 더 편리하고 체계적으로 <br>
테스트를 실행하고 그 결과를 확인하는 방법이 절실히 필요

---

<div id="2.2"></div>

## 2.2 UserDaoTest 개선


### 2.2.1 테스트 검증의 자동화

*테스트 :*

User 오브젝트를 add()를 통해 등록 후 get()으로 DB 에서 가져온 User 오브젝트의

정보가 서로 정확히 일치하는 것



*결과  ( 성공 / 테스트 에러(에러 발생) / 테스트 실패(원하는 결과X) ) :*

테스트 에러의 경우, 콘솔에 에러 메시지와 호출 스택 정보가 출력 되므로 알기 쉬움

테스트 실패하는 것은 별도의 확인 작업과 그 결과가 있어야만 알 수 있음



=> 테스트코드에서 결과를 직접 확인하고, 기대한 결과와 달라서 실패했을 경우에는 테스트 실패 라는

메시지를 출력하도록 수정



*수정 전 테스트 코드*
<pre>
	System.out.println(user2.getName());
	System.out.println(user2.getPassword());			
	System.out.println(user2.getId() + " 조회 성공");
</pre>



*수정 후 테스트 코드*
<pre>
	if( !user.getName().equals(user2.getName()) ) {
		System.out.println("테스트 실패 (name)");
	} else if ( !user.getPassword().equals(user2.getPassword()) ) {
		System.out.println("테스트 실패 (password)");
	} else {
		System.out.println("조회 테스트 성공");			
	}
</pre>


=> 위와 같이 수정하면, 마지막 메시지가 "테스트 성공"이라고 나오는지 확인하는 것 뿐!



### 2.2.2 테스트의 효율적인 수행과 결과 관리

main() 메소드로 테스트를 하는 것은 많은 한계점을 지니고 있음

=> 이미 자바에서는 단순하면서도 실용적인 테스트를 위한 도구가 여러 가지 존재


**JUnit 테스트로 전환**

JUnit은 프레임워크 ( 기본 동작원리 : 제어의 역전IoC )


**테스트 메소드 전환**

테스트가 main() 메소드로 만들어졌다는 건 제어권을 직접 갖는다는 의미에서

프레임워크에 적용하기엔 적합하지 않음! => 새로운 테스트 메소드로 변환

( => 1) public // 2) @Test 애노테이션)


*JUnit 프레임워크에서 동작할 수 있는 테스트 메소드로 전환*
<pre>
	@Test //JUnit에게 테스트용 메소드임을 알림
	public void addAndGet() throws SQLException {			
		ApplicationContext context
			= new GenericXmlApplicationContext("applicationContext.xml");
		...
	}
</pre>



**검증 코드 전환**

테스트의 결과를 검증하는 if/else 문장을 JUnit이 제공하는 방법을 이용해서 전환

<pre>
if(!user.getName().equals(user2.getName())) {...}
==assertThat( user2.getName(), is(user.getName()) );
</pre>



*JUnit을 적용한 UserDaoTest*
<pre>
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import springbook.user.domain.User;

public class UserDaoTest {
	@Test
	public void addAndGet() throws SQLException {		
		ApplicationContext context
			= new GenericXmlApplicationContext("applicationContext.xml");

		UserDao dao = context.getBean("userDao", UserDao.class);

		User user = new User();
		user.setId("gyumee");
		user.setName("백기선");
		user.setPassword("springno1");

		dao.add(user);

		User user2 = dao.get(user.getId());

		assertThat( user2.getName(), is(user.getName()) );
		assertThat( user2.getPassword(), is(user.getPassword()) );		
	}
}
</pre>



**JUnit 테스트 실행**

JUnit 프레임워크를 이용해 앞에서 만든 테스트 메소드를 실행하도록 코드 구현

스프링 컨테이너와 마찬가지로 JUnit 프레임워크도 자바 코드로 만들어진 프로그램이므로

어디선가 한 번은 JUnit 프레임워크를 시작시켜 줘야 함


*JUnit을 이용해 테스트를 실행해주는 main() 메소드*
<pre>
import org.junit.runner.JUnitCore;
...
	public static void main(String[] args) {
		JUnitCore.main("springbook.user.dao.UserDaoTest");
	}
</pre>  

---

<div id="2.3"></div>  

## 2.3 개발자를 위한 테스팅 프레임워크 JUnit


### 2.3.1 JUnit 테스트 실행 방법

JUnitCore를 이용해 테스트를 실행하고 콘솔에 출력된 메시지를 보며 결과 확인은

간단하지만, 테스트의 수가 많아지면 관리하기 힘들어지는 단점이 존재

=> 가장 좋은 JUnit 테스트 실행 방법은 자바 IDE에 내장 된 JUnit 테스트 지원 도구 이용


**IDE**

alt + Shift + X + T : 선택해둔 클래스나 패키지, 프로젝트의 테스트가 바로 실행





### 2.3.2 테스트 결과의 일관성

기존에는 매번 UserDaoTest 실행하기 전에 DB의 USER 테이블 데이터를 모두 삭제하는 불편함 존재

=> 테스트가 외부 상태에 따라 성공 or 실패하기도 한다는 점

=> addAndGet() 테스트 후 테스트가 등록한 사용자 정보를 삭제해서, 테스트를 수행하기 이전 상태로

만드는 것이 가장 좋음


**deleteAll()과 getCount() 추가**

- deleteAll()
<pre>
	public void deleteAll() throws SQLException {
		Connection c = this.dataSource.getConnection();
		PreparedStatement ps = c.prepareStatement("delete from users");
		ps.executeUpdate();

		ps.close();
		c.close();
	}
</pre>

- getCount()
<pre>
	public int getCount() throws SQLException {
		Connection c = this.dataSource.getConnection();
		PreparedStatement ps = c.prepareStatement("select count(*) from users");
		ResultSet rs = ps.executeQuery();
		rs.next();
		int count = rs.getInt(1);

		rs.close();
		ps.close();
		c.close();

		return count;
	}
</pre>




**deleteAll()과 getCount()의 테스트**

독립적으로 자동 실행되는 테스트를 만들기 애매함

=> 새로운 테스트를 만들기보다는 기존에 만든 addAndGet() 테스트를

확장하는 방법이 더 나음

기존 문제 : 수동으로 USER 테이블의 내용을 모두 삭제

-> deleteAll()을 이용하여 테스트가 시작될 때 실행하도록 수정

또 다른 문제 : deleteAll()은 자체 검증 X -> getCount() 추가

getCount()가 잘 동작하는 지 확인하기 위해 add() 후 getCount() 호출


*deleteAll()과 getCount()가 추가된 addAndGet() 테스트*
<pre>
	@Test
	public void andAndGet() throws SQLException {
		ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
		UserDao dao = context.getBean("userDao", UserDao.class);

		dao.deleteAll();
		assertThat( dao.getCount(), is(0) );

		User user = new User();
		user.setId("gyumee");
		user.setName("박성철");
		user.setPassword("springno1");

		dao.add(user);
		assertThat( dao.getCount(), is(1) );


		User user2 = dao.get(user.getId());
		assertThat(user2.getName(), is(user.getName()));
		assertThat(user2.getPassword(), is(user.getPassword()));
	}
</pre>



**동일한 결과를 보장하는 테스트**

DB의 테이블 삭제  작업 없이, 어떤 경우에 실행해도 동일한 결과 나옴

=> 테스트가 어떤 상황에서 반복적으로 실행된다고 하더라도

동일한 결과가 나올 수 있게 된 것

=> 단위 테스트는 항상 일관성 있는 결과가 보장돼야 한다는 점을 기억!

(테스트를 실행하는 순서를 바꿔도 동일한 결과가 보장되도록 만들어야 함)



### 2.3.3 포괄적인 테스트

getCount() 메소드를 테스트에 적용하면서 DB가 비었을 떄 0, add() 후 1을 반환하는 것을

확인할 수 있었음. 그러나 미처 생각하지 못한 문제가 숨어 있을지도 모르니

더 꼼꼼한 테스트를 해보는 것이 좋은 자세!

테스트를 안 만드는 것도 위험 + 성의 없이 테스트를 만드는 바람에 문제가 있는 코드인데도

테스트가 성공하게 만드는 건 더 위험함


**getCount() 테스트**

getCount()를 더 꼼꼼히 테스트하기 위해, 여러 개의 User를 등록해가면서 getCount()의 결과를 매번 확인


시나리오 : <br>
- USER 테이블 데이터 모두 삭제 후 getCount() == 0
- 3개의 사용자 정보를 하나씩 추가하면서 getCount()가 하나씩 증가하는 지 확인


*파라미터가 있는 User 클래스 생성자*
<pre>
public class User {
	String id;
	String name;
	String password;

	public User(String id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
	}

	public User() {
		//empty
	}
	...
}
</pre>



*getCount() 테스트*
<pre>
	@Test
	public void count() throws SQLException {
		ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
		UserDao dao = context.getBean("userDao", UserDao.class);

		User user1 = new User("gyumee","박성철","springno1");
		User user2 = new User("leegw700","이길원","springno2");
		User user3 = new User("bumjin","박범진","springno3");

		dao.deleteAll();
		assertThat( dao.getCount(), is(0) );

		dao.add(user1);
		assertThat( dao.getCount(), is(1) );

		dao.add(user2);
		assertThat( dao.getCount(), is(2) );

		dao.add(user3);
		assertThat( dao.getCount(), is(3) );		
	}
</pre>


=> 실행 시 정상적으로 실행 BUT JUnit은 테스트의 메소드에 순서를 보장해 주지 않음

=> 테스트 결과가 실행 순서에 영향을 받는 다면 테스트를 잘못 만든 것!


**addAndGet() 테스트 보완**

id를 조건으로 사용자를 검색하는 기능을 가진 get()에 대한 테스트는 조금 부족한 감이 있음

get()이 파라미터로 주어진 id에 해당하는 사용자를 가져온 것인지, 그냥 아무거나 가져온 것인지

테스트에서 검증하지는 못함 ( 하나 추가 후 검색 하므로 )

*get() 테스트 기능을 보완한 addAndGet() 테스트*
<pre>
	@Test
	public void andAndGet() throws SQLException {
		ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
		UserDao dao = context.getBean("userDao", UserDao.class);

		// 중복되지 않는 값을 가진 두 개의 User 오브젝트를 준비해둠
		User user1 = new User("gyumee","박성철","springno1");
		User user2 = new User("leegw700","이길원","springno2");

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
</pre>



**get() 예외조건에 대한 테스트**

생각할 점 : dao.get()에 전달 된 id 값에 해당하는 사용자 정보가 없다면 ?

1. null과 같은 특별한 값을 티너
2. id에 해당하는 정보를 찾을 수 없다고 예외 던지기

=> 각각 장단점이 있지만, 후자의 방법을 통해 예제를 살펴봄

=> 직접 예외를 정의해도 되고 스프링이 미리 정의해 놓은 예외를 가져다 써도 됨 ( 뒤에서 자세히 )

=> 스프링의 EmptyResultDataAccessException 예외를 이용

=> 일반적으로 테스트 중에 예외가 던져지면, 테스트 메소드 실행은 중단 되고

테스트는 실패 됨



*get() 메소드의 예외상황에 대한 테스트*
<pre>
import org.springframework.dao.EmptyResultDataAccessException;
...

	// expected == 테스트 중에 발생할 것으로 기대하는 예외 클래스를 지정
	@Test(expected=EmptyResultDataAccessException.class)
	public void getUserFailure() throws SQLException {
		ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");

		UserDao dao = context.getBean("userDao", UserDao.class);
		dao.deleteAll();
		assertThat( dao.getCount(), is(0) );

		// 이 메소드 실행 중에 예외가 발생해야 함
		// 예외가 발생하지 않으면 테스트가 실패
		dao.get("unkown_id");		
	}
</pre>



*데이터를 찾지 못하면 예외를 발생시키도록 수정한 get() 메소드*
<pre>
	c = this.dataSource.getConnection();
	ps = c.prepareStatement("select * from users where id = ?");
	ps.setString(1, id);

	rs = ps.executeQuery();

	User user = null;
	if( rs.next() ) {
		user = new User();
		user.setId(rs.getString("id"));
		user.setName(rs.getString("name"));
		user.setPassword(rs.getString("password"));
	}			

	if( user == null )
		throw new EmptyResultDataAccessException(1);			
	return user;
</pre>

=> 정상적으로 테스트 메소드를 마치면 테스트가 실패 // exptected에서 지정한 예외가 던져지면 테스트가 성공




**포괄적인 테스트**

위의 간단한 DAO는 굳이 이런 다양한 테스트를 하지 않고 코드만 살펴봐도 문제가

생기지 않으리라고 자신할 수 있을지 모르지만, 이렇게 DAO의 메소드에 대한 포괄적인 테스트를 만들어

두는 편이 훨씬 안전하고 유용하다.

또한, 개발자가 자주하는 실수 중 하나는 성공하는 테스트만 골라서 만드는 것.

스프링 창시자인 로드 존슨은 "항상 네거티브 테스트를 먼저 만들라"고 조언을 함

=> 테스트를 작성할 때 부정적인 케이스를 먼저 만드는 습관을 들이는 것이 좋음

e.g: get()의 경우 존재하는 id가 주어졌을 때 해당 레코드를 정확히 가져오는 가를

테스트 하는 것도 중요하지만, 존재하지 않는 id가 주어졌을 때는 어떻게 반응할지를

먼저 결정하고 이를 확인할 수 있는 테스트를 먼저 만들려고 한다면, 예외적인 상황을

빠드리지 않는 꼼꼼한 개발이 가능!!




### 2.3.4 테스트가 이끄는 개발

UserDao의 경우 새로운 기능을 넣기 위해 UserDao 코드를 수정하고 이를 검증하기 위한 테스트 코드를

작성한 것이 아니라, 테스트를 먼저 만들어 테스가 실패하는 것을 보고 나서 UserDao의 코드를 수정



**기능 설계를 위한 테스트**

-> '존재하지 않는 id로 get() 메소드를 실행하면, 특정한 예외를 던져야 한다' 는 식으로

만들어야 할 기능을 결정.

-> UserDao 코드 수정 대신, getUserFailure() 테스트를 먼저 만듬

==> 만들어진 코드를 보며 이것을 어떻게 테스트할까 라고 생각하기 보다

추가하고 싶은 기능을 코드로 표현하려고 했기 때문에 가능!


*getUserFailure() 테스트 코드에 나타난 기능*


<table style="width:70%">
	<tr>
		<th>&nbsp;</th>
		<th>단계</th>
		<th>내용</th>
		<th>코드</th>
	</tr>
	<tr>
		<td>조건</td>
		<td>어떤 조건을 가지고</td>
		<td>가져올 사용자 정보가 존재하지 않는 경우에</td>
		<td>
			dao.deleteAll(); <br>
			assertThat( dao.getCount(), is(0) );
		</td>
	</tr>
	<tr>
		<td>행위</td>
		<td>무엇을 할 때</td>
		<td>존재하지 않는 id로 get()을 실행하면</td>
		<td>get("unknown_id")</td>
	</tr>
	<tr>
		<td>결과</td>
		<td>어떤 결과가 나온다</td>
		<td>특별한 예외가 던져진다.</td>
		<td>
			@Test(expected= <br>
			EmptyResultDataAccessException.class)
		</td>
	</tr>
</table>


**테스트 주도 개발(TDD, Test Driven Development)**

; 만들고자 하는 기능의 내용을 담고 있으면서 만들어진 코드를 검증도 해줄 수 있도록

테스트 코드를 먼저 만들고, 테스트를 성공하게 해주는 코드를 작성하는 방식의 개발 방법

"실패한 테스트를 성공시키기 위한 목적이 아닌 코드는 만들지 않는다"가 TDD 기본 원칙



개발자가 정신없이 개발을 하다 보면, 사이사이 테스트를 만들어서 코드를 점검할 타이밍을

놓치는 경우가 많음 -> 나중에는 귀찮아져서 성의 없이 코드를 작성

BUT TDD는 아예 테스트를 먼저 만들고 그 테스트가 성공하도록 하는 코드만 만드는 식으로

진행하기 때문에 테스트를 뺴먹지 않고 꼼꼼하게 만들어낼 수 있음

( +추가적으로 TDD에 대한 이점 )



### 2.3.5 테스트 코드 개선

*기존 중복되는 부분*
<pre>
ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
UserDao dao = context.getBean("userDao", UserDao.class);		
</pre>


=> 메소드 추출 리팩토링 외에 JUnit이 제공하는 기능을 활용

=> jUnit 프레임 워크는 테스트 메소드를 실행할 때 부가적으로 해주는 작업이 몇 가지 있음

**@Before**

: 테스트를 실행할 때마다 반복되는 준비 작업을 별도의 메소드에 넣게 해주고

이를 매번 테스트 메소드를 실행하기 전에 먼저 실행시켜주는 기능


*중복 코드를 제거한 UserDaoTest*
<pre>
...
public class UserDaoTest {
	// setUp() 메소드에서 만드는 오브젝트를 테스트 메소드에서
	// 사용할 수 있도록 인스턴스 변수로 선언
	private UserDao dao;

	// JUnit이 제공하는 애노테이션.
	// @Test 메소드가 실행되기 전에 먼저 실행돼야
	// 하는 메소드를 정의
	@Before
	public void setUp() {
		ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
		dao = context.getBean("userDao", UserDao.class);
	}
	...
}
</pre>


*JUnit이 하나의 테스트 클래스를 가져와 테스트를 수행하는 방식(간단히 정리)*

- 테스트 클래스에서 @Test가 붙은 public 이고 void형이며 파라미터가 없는 테스트 메소드를 모두 찾음
- 테스트 클래스의 오브젝트를 하나 만듬
- @Before가 붙은 메소드가 있으면 실행
- @Test가 붙은 메소드를 하나 호출하고 테스트 결과를 저장
- @After가 붙은 메소드가 있으면 실행
- 나머지 테스트 메소드에 대해 2~5번 반복
- 모든 테스트의 결과를 종합해서 돌려줌


주의 :

- @Before,@After 메소드를 테스트 메소드에서 직접 호출하지 않기 때문에,
서로 주고받을 정보가 있으면 인스턴스 변수로 사용
- 각 테스트 메소드 실행할 때마다 테스트 클래스의 오브젝트를 새로 만듬


![JUnit의 테스트 메소드 실행 방법](./pics/pic-2-4.png)


=> JUnit 개발자는 각 테스트가 서로 영향을 주지 않고 독립적으로 실행됨을 확실히 보장해주기 위해,

매번 새로운 오브젝트를 만들게 함

=> 그래서 인스턴스 변수도 부담 없이 사용할 수 있음 ( 다음 테스트 시 , 새로운 오브젝트 )



**픽스처(fixture)**

; 테스트를 수행하는 데 필요한 정보나 오브젝트

일반적으로 픽스처는 여러 테스트에서 반복적으로 사용되기 때문에 @Before 메소드를

이용해 생성해두면 편리함 ( UserDaoTest의 UserDao )


*User픽스처를 적용한 UserDaoTest*
<pre>
...
public class UserDaoTest {
	private UserDao dao;
	private User user1;
	private User user2;
	private User user3;

	@Before
	public void setUp() {
		...		
		user1 = new User("gyumee","박성철","springno1");
		user2 = new User("leegw700","이길원","springno2");
		user3 = new User("bumjin","박범진","springno3");
	}
	...
}
</pre>

---

<div id="2.4"></div>

## 2.4 스프링 테스트 적용


문제점 :

@Before 메소드가 테스트 메소드 개수만큼 반복

=> 빈이 많아지고 복잡해지면, 애플리케이션

컨텍스트 생성에 적지 않은 시간이 걸릴 수 있음

(리소스 차지 등등)



문제점 :

애플리케이션 컨텍스트 생성 방식

( @Before 메소드가 테스트 메소드 개수만큼 반복 )


### 2.4.1 테스트를 위한 애플리케이션 컨텍스트 관리

; 스프링은 JUnit을 이용하는 테스트 컨텍스트 프레임워크를 제공

=> 테스트 컨텍스트의 지원을 받으면, 간단한 애노테이션 설정 만으로

테스트에서 필요로 하는 애플리케이션 컨텍스트를 만들어서 모든 테스트가 공유하게

할 수 있음



**스프링 테스트 컨텍스트 프레임 워크 적용**

*스프링 테스트 컨텍스트를 적용한 UserDaoTest*
<pre>
...
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

// 스프링 테스트 컨텍스트 프레임 워크의 JUnit확장 기능 지정
@RunWith(SpringJUnit4ClassRunner.class)
// 테스트 컨텍스트가 자동으로 만들어줄 애플리케이션 컨텍스트의 위치 지정
@ContextConfiguration(locations="/applicationContext.xml")
public class UserDaoTest {
	// 테스트 오브젝트가 만들어지고 나면 스프링 테스트
	// 컨텍스트에 의해 자동으로 값이 주입된다.
	@Autowired
	private ApplicationContext context;
	..
}
</pre>


*테스트 메소드의 컨텍스트 공유*

<pre>
...
	@Before
	public void setUp() {		
		System.out.println(this.context);
		System.out.println(this);
...
</pre>


*실행 결과(주소값만)*

<pre>
@6c629d6e <br>
@462d5aee <br>
@6c629d6e <br>
@62fdb4a6 <br>
@6c629d6e <br>
@27c6e487 <br>
</pre>

=> context는 모두 동일하고 UserDaoTest는 매번 다른 인스턴스

=> 스프링의 JUnit 확장기능은 테스트가 실행되기 전에 딱 한 번만

애플리케이션 컨텍스트를 만들어두고, 테스트 오브젝트가 만들어질 때마다

특별한 방법을 이용해 애플리케이션 컨텍스트 자신을 테스트 오브젝트의

특정 필드에 주입

(수행 시간도 1.34, 0.15 , 0.06초 로 초기 할당 시만 길고 나머지는 빨라짐)



**테스트 클래스의 컨텍스트 공유**

; 스프링 테스트 컨텍스트 프레임워크는 여러 테스트 클래스 사이에서도

애플리케이션 컨텍스트를 공유하게 해줌

<pre>
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/applicationContext.xml")
public class UserDaoTest{..}

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/applicationContext.xml")
public class GroupDaoTest{..}
</pre>

=> *위와 같이 있을 때 모두 하나의 애플리케이션 컨텍스트만 만들어져서 같이 이용*


**@Autowired**

; 스프링의 DI에 사용되는 특별한 애노테이션 (타입에 의한 자동와이어링)

- 변수 타입과 일치하는 컨텍스트 내의 빈을 찾음

- 일치하는 빈이 있으면, 인스턴스 변수에 주입

- 타입이 일치하는 빈이 2개이면, 변수 이름으로 빈 찾고 <br>
찾을 수 없으면 예외 발생


<pre>
...
public class UserDaoTest {
	// UserDao 타입 빈을 직접 DI 받음
	@Autowired
	private UserDao dao;
	...
}
</pre>

=> @Autowired는 변수에 할당 가능한 타입을 가진 빈을 자동으로 찾음

(단, 같은 타입의 빈이 두 개 이상 있는 경우에는 타입만으로는 빈을 결정 X)

SimpleDriverDataSource vs DataSource 타입 ??

=> DataSource에 정의 된 타입만 사용하고 싶으면 DataSource로 받는 게 더 좋고

꼭 필요하지 않다면 테스트에서도 가능한 한 인터페이스를 사용해서 애플리케이션 코드와

느슨하게 연결해두는 편이 좋음 (DB 연결 정도 등 SimpleDriverDataSource에만

정의 된 것을 사용하고자 할때는 타입 바꿔도 괜찮음)


### 2.4.2 DI와 테스트

기존에는 UserDao와 DB 커넥션 생성 클래스 사이에는 DataSource 인터페이스를

둠으로써, UserDao는 자신이 사용하는 오브젝트의 클래스가 무엇인지 알 필요가 없었음

BUT 절대로 DataSource의 구현 클래스를 바꾸지 않을 것이고, 개발하는 시스템은

운영 중에 항상 SimpleDriverDataSource를 통해서만 DB 커넥션을 가져올 것인데

굳이 DataSource 인터페이스를 사용하고 DI를 주입해주는 방식을 이용해야 하는 가?

==> 그래도 인터페이스를 두고 DI를 적용해야 한다.

1. 소프트웨어 개발에서 절대로 바뀌지 않는 것은 없음 <br>
클래스 대신 인터페이스, new 대신 DI를 주입받게 하는 건 아주 단순하고 쉬운 작업.<br>
당장에는 클래스를 바꿔서 사용할 계획이 전혀 없더라도, 언젠가 변경이 필요한 상황이 닥쳤을 때<br>
수정에 들어가는 시간과 비용의 부담을 줄여줄 수 있다면 인터페이스&DI를 적용하는 작은 수고를<br>
하지 않을 수 없음  <br>

2. 클래스의 구현 방식은 바뀌지 않는다고 하더라도 인터페이스를 두고 DI를 적용하게 되면 <br>
다른 차원의 서비스 기능을 도입할 수 있음 <br>
( e.g:1장에서 만들었던 DB 커넥션 카운팅 )<br>
이런 기법을 일반화해서 AOP라는 기술로 만들어 주기도 함<br>

3. 테스트 때문(단지 효율적인 테스트를 손쉽게 만들기 위해서라도 DI를 적용)


**테스트 코드에 의한 DI**

애플리케이션이 사용할 applicationContext.xml에 정의된 DataSource 빈은

서버의 DB 풀 서비스와 연결해서 운영용 DB 커넥션을 돌려주도록 만들어져 있다고 가정.

=> 테스트 시, 운영용 DB 를 사용하면 안됨(잘못 건들 수도 있고 등등)

=> 테스트 / 배치 시, 다르게 설정하는 것보다 테스트 시 직접 DataSource 오브젝트를

바꿔주는 방법을 이용!


*테스트를 위한 수동 DI를 적용한 UserDaoTest*
<pre>
...
/* 테스트 메소드에서 애플리케이션 컨텍스트의 구성이나
     상태를 변경한다는 것을 테스트 컨텍스트 프레임 워크에 알려줌 */
@DirtiesContext
public class UserDaoTest {
	@Autowired
	private UserDao dao;
	@Before
	public void setUp() {
		...
		// 테스트에서 UserDao가 사용할 DataSource
		// 오브젝트를 직접 생성		
		DataSource dataSource = new SingleConnectionDataSource (
				"jdbc:mysql://localhost/testdb","spring","book",true );
		dao.setDataSource(dataSource); // 코드에 의한 수동 DI
	}
	..
}		
</pre>


<table>
	<tr>
		<th>장점</th>
		<td>XML설정 파일을 수정하지 않고도 테스트 코드를 통해 오브젝트 관계를 재구성할 수 있음</td>
	</tr>
	<tr>
		<th>주의</th>
		<td>컨텍스트를 공유하므로, 변경하지 않는 것이 원칙</td>
	</tr>
	<tr>
		<td colspan="2">@DirtiesContext 애노테이션 추가 <br>
		(스프링의 테스트 컨텍스트 프레임워크에게 해당 클래스의 테스트에서 애플리케이션 컨텍스트의
		상태를 변경한다는 것을 알려줌 {메소드 레벨에서도 사용 가능})
		</td>
	</tr>
</table>



**테스트를 위한 별도의 DI 설정**

=> 테스트 코드에서 빈 오브젝트에 수동으로 DI 하는 방법은 단점이 더 많음

=> 테스트에서 사용 될 전용 설정파일을 따로 만들기


*테스트 DB 변경 설정 test-applicationContext.xml*
<pre>
&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
&lt;beans xmlns=&quot;http://www.springframework.org/schema/beans&quot;
	xmlns:xsi=&quot;http://www.w3.org/2001/XMLSchema-instance&quot;
	xsi:schemaLocation=&quot;http://www.springframework.org/schema/beans
						http://www.springframework.org/schema/beans/spring-beans-3.0.xsd&quot;&gt;

	&lt;bean id=&quot;dataSource&quot; class=&quot;org.springframework.jdbc.datasource.SimpleDriverDataSource&quot;&gt;
		&lt;property name=&quot;driverClass&quot; value=&quot;com.mysql.jdbc.Driver&quot; /&gt;
		&lt;property name=&quot;url&quot; value=&quot;jdbc:mysql://localhost/testdb?characterEncoding=UTF-8&quot; /&gt;
		&lt;property name=&quot;username&quot; value=&quot;spring&quot; /&gt;
		&lt;property name=&quot;password&quot; value=&quot;book&quot; /&gt;
	&lt;/bean&gt;

&lt;/beans&gt;

</pre>


*테스트용 설정 파일 적용*
<pre>
...
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/test-applicationContext.xml")
public class UserDaoTest {
...
</pre>


**컨테이너 없는 DI 테스트**

; DI를 테스트에 이용하는 방법은 아예 스프링 컨테이너를 사용하지 않고

테스트를 만드는 것.


=> UserDaoTest는 사실 UserDao 코드가 DAO로서 DB에 정보를 잘 등록하고 잘 가져오는지만 확인

=> 스프링 컨테이너에서 UserDao가 동작함을 확인하는 일은 UserDaoTest의 기본적인 관심 X

=> 컨테이너를 이용하지 않고, 직접 생성하기


*애플리케이션 컨텍스트 없는 DI 테스트*
<pre>
...
public class UserDaoTest {
	private UserDao dao;
	...
	@Before
	public void setUp() {
		...
		//오브젝트 생성, 관계설정 등을 모두 직접 해주기
		dao = new UserDao();		
		DataSource dataSource = new SingleConnectionDataSource (
				"jdbc:mysql://localhost/testdb","spring","book",true );
		dao.setDataSource(dataSource);
		...
	}
	...
}
</pre>


*침투적 기술과 비침투적 기술*

침투적(invasive) 기술 : 기술을 적용했을 때 애플리케이션 코드에 기술 관련

API가 등장하거나, 특정 인터페이스나 클래스를 사용하도록 강제하는 기술

=> 애플리케이션 코드가 해당 기술에 종속되는 결과


비침투적(noninvasive) 기술 : 애플리케이션 로직을 담은 코드에 아무런

영향을 주지 않고, 적용이 가능

=> 기술에 종속적이지 않은 순수한 코드를 유지할 수 있게 해줌 (e.g 스프링)

---

<div id="2.5"></div>  

## 2.5 학습 테스트로 배우는 스프링

; 개발자는 자신이 만든 코드가 아닌 다른 사람이 만든 코드와 기능에 대한 테스트를

작성할 필요가 있을 까?

=> 일반적으로, 자신이 만들고 있는 코드에 대한 테스트만 작성하면 되지만,

때로는 자신이 만들지 않은 프레임워크나 다른 개발팀에서 만들어서 제공한 라이브러리 등에

대해서도 테스트를 작성해야 함

=> 학습 테스트(learning test)

( 자신이 사용할 API나 프레임워크의 기능을 테스트로 보면서, 사용 방법을 익히려는 것 )



### 2.5.1 학습 테스트의 장점

- 다양한 조건에 따른 기능을 손쉽게 확인할 수 있다. <br>
자동화된 테스트의 모든 장점이 학습 테스트에도 그대로 적용

- 학습 테스트 코드를 개발 중에 참고할 수 있다 <br>
수동으로 예제를 만드는 방법은, 코드를 계속 수정해가면서 기능을 확인해보기 때문에<br>
결국 최종 수정한 예제 코드만 남음. BU 학습 테스트는 다양한 기능과 조건에 대한<br>
테스트 코드를 개별적으로 만들고 남겨둘 수 있음.<br>

- 프레임워크나 제품을 업그레이드할 때 호환성 검증을 도와줌

- 테스트 작성에 대한 좋은 훈련이 된다.<br>

- 새로운 기술을 공부하는 과정이 즐거워진다.



### 2.5.2 학습 테스트 예제


**JUnit 테스트 오브젝트 테스트**

Q. JUnit은 테스트 메소드를 수행할 때마다 새로운 오브젝트를 만든다고 했음 <br>
정말 매번 새로운 오브젝트가 만들어질까? 최신 버전에서 달라지지 않았을까? 등


*JUnit 테스트 오브젝트 생성에 대한 학습 테스트*
<pre>
package springbook.learningtest.junit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class JUnitTest {
	static JUnitTest testObject;

	@Test
	public void test1() {
		assertThat( this, is(not( sameInstance(testObject) ) ) ) ;
		testObject = this;
	}

	@Test
	public void test2() {
		assertThat( this, is(not( sameInstance(testObject) ) ) ) ;
		testObject = this;
	}

	@Test
	public void test3() {
		assertThat( this, is(not( sameInstance(testObject) ) ) ) ;
		testObject = this;
	}
}
</pre>

=> 위의 코드는, 순서에 따라 1,3이 같을 수도 있음


*개선한 JUnit 테스트 오브젝트 생성에 대한 학습 테스트*
<pre>
package springbook.learningtest.junit;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;


import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class JUnitTest {
	static Set<JUnitTest> testObjects = new HashSet<JUnitTest>();

	@Test
	public void test1() {
		assertThat( testObjects, not(hasItem(this)) ) ;
		testObjects.add(this);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test2() {
		assertThat( testObjects, not(hasItem(this)) ) ;
		testObjects.add(this);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test3() {
		assertThat( testObjects, not(hasItem(this)) ) ;
		testObjects.add(this);
	}

	private boolean hasItem(JUnitTest jUnitTest) {
		return testObjects.contains(jUnitTest);
	}
}
</pre>




**스프링 테스트 컨텍스트 테스트**

*JUnit 테스트를 위한 빈 설정 파일 : junit.xml*
<pre>
&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;

&lt;beans xmlns=&quot;http://www.springframework.org/schema/beans&quot;
	xmlns:xsi=&quot;http://www.w3.org/2001/XMLSchema-instance&quot;
	xsi:schemaLocation=&quot;http://www.springframework.org/schema/beans
						http://www.springframework.org/schema/beans/spring-beans-3.0.xsd&quot;&gt;


&lt;/beans&gt;						

</pre>


*스프링 테스트 컨텍스트에 대한 학습 테스트*

<pre>
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
}
</pre>




### 2.5.3 버그 테스트

;버그 발생 시, 무턱대고 코드를 뒤져가면서 수정하려고 하기보다는 <br>
먼저 버그 테스트를 만들어보는 편이 유용

**버그 테스트의 필요성과 장점**

- 테스트의 완성도를 높여준다. <br>
 기존 테스트에ㅓ 미처 검증하지 못했던 부분이 있기 때문에 오류가 발생<br>
> 이에 대해 테스트를 만들면 불충분했던 테스트를 보완<br>
> 이후 비슷한 문제가 다시 등장하더라도, 이전에 만들었던 버그 테스트 덕분에 쉽게 추적 가능<br>

- 버그의 내용을 명확하게 분석하게 해준다. <br>
 테스트로 만들어서 실패하게 하려면, 어떤 이유 떄문에 문제가 생겼는지 명확히 <br>
알아야 한다. 따라서 버그를 좀 더 효과적으로 분석할 수 있다.<br>

- 기술적인 문제를 해결하는 데 도움이 된다.<br>


*동등분할(equivalence partitioning)*<br>
같은 결과를 내는 값의 범위를 구분해서 각 대표 값으로 테스트를 하는 방법<br>
어떤 작업의 결과의 종류가 true, false 또는 예외발생 세 가지라면<br>
결과를 내는 입력 값이나 상황의 조합을 만들어 모든 경우에 대한 테스트를 해보는 것이 좋음<br>

*경게값 분석(boundary value analysis)*<br>
에러는 동등분할 범위의 경계에서 주로 많이 발생한다는 특징을 이용해서 경계의 근처에 있는 값을 <br>
이용해 테스트하는 방법<br>
보통 숫자의 입력 값인 경우 0이나 그 주변 값 또는 정수의 최대값, 최소값 등으로<br>
테스트해보면 도움이 될 때가 많음<br>

---

<div id="2.6"></div>  

## 2.6 정리

- 테스트는 자동화돼야 하고, 빠르게 실행할 수 있어야 한다.

- main() 테스트 대신 JUnit 프레임워크를 이용한 테스트 작성이 편리하다.

- 테스트 결과는 일관성이 있어야 한다. 코드의 변경 없이 환경이나 테스트 실행 순서에 따라 <br>
  서 결과가 달라지면 안 된다.

- 테스트는 포괄적으로 작성해야 한다. 충분한 검증을 하지 않는 테스트는 없는 것보다 나쁠 수 있다.

- 코드 작성과 테스트 수행의 간격이 짧을수록 효과적이다.

- 테스트하기 쉬운 코드가 좋은 코드이다.

- 테스트를 먼저 만들고 테스트를 성공시키는 코드를 만들어 가는 테스트 주도<br>
개발 방법도 유용하다.

- 테스트 코드도 애플리케이션 코드와 마찬가지로 적절한 리팩토링이 필요하다.

- @Before, @After를 사용해서 테스트 메소드들의 공통 준비 작업과 정리 작업을 처리할 수 있다.

- 스프링 테스트 컨텍스트 프레임워크를 이용하면 테스트 성능을 향상시킬 수 있다.

- 동일한 설정파일을 사용하는 테스트는 하나의 애플리케이션 컨텍스트를 공유한다.

- @Autowired를 사용하면 컨텍스트의 빈을 테스트 오브젝트에 DI 할 수 있다.

- 기술의 사용 방법을 익히고 이해를 돕기 위해 학습 테스트를 작성하자.

- 오류가 발견될 경우 그에 대한 버그 테스트를 만들어 두면 유용하다.

---
