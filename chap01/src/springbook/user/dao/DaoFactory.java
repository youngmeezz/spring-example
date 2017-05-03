package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*	
 * ������ �� ���丮�� ���� ������Ʈ ������
 * ����ϴ� Ŭ������� �ν��� �� �ֵ��� => @Configuration 
 */ 	
@Configuration
public class DaoFactory {	
	
	//������Ʈ ������ ����ϴ� IoC �� �޼ҵ��� ǥ��
	@Bean
	public UserDao userDao() {
		return new UserDao(connectionMaker());
	}
	
	@Bean
	public ConnectionMaker connectionMaker() {
		// �и��ؼ� �ߺ��� ������ 
		// ConnectionMaker Ÿ�� ������Ʈ �����ڵ�
		return new DConnectionMaker();
	}
}
