package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*	
 * 스프링 빈 팩토리를 위한 오브젝트 설정을
 * 담당하는 클래스라고 인식할 수 있도록 => @Configuration 
 */ 	
@Configuration
public class DaoFactory {	
	
	//오브젝트 생성을 담당하는 IoC 용 메소드라는 표시
	@Bean
	public UserDao userDao() {
		return new UserDao(connectionMaker());
	}
	
	@Bean
	public ConnectionMaker connectionMaker() {
		// 분리해서 중복을 제거한 
		// ConnectionMaker 타입 오브젝트 생성코드
		return new DConnectionMaker();
	}
}
