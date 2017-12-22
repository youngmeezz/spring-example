package org.zerock.web;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.MemberVO;
import org.zerock.persistence.MemberDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class MemberDAOTest {
	@Inject
	private MemberDAO dao;
	
	@Test
	public void testTime()throws Exception {
		System.out.println(dao.getTime());
	}
	
	@Test
	public void testInsertMember() throws Exception {
		MemberVO vo = new MemberVO();
		vo.setUserid("user11");
		vo.setUserpw("user11");
		//vo.setUsername("USER00");
		vo.setUsername("하이바");
		vo.setEmail("user11@aaa.com");		
		dao.insertMember(vo);
	}
	
	@Test
	public void testSelectMember()throws Exception {
		MemberVO member1 = dao.readMember("user00");
		if(member1!=null)
			System.out.println(member1);
		
		MemberVO member2 = dao.readWithPw("user00","user00");
		if(member2!=null)
			System.out.println(member2);
		
		MemberVO member3 = dao.readWithPw("user00","user01");
		if(member3==null)
			System.out.println("비번 틀림");
		else
			System.out.println(member3);
		
		MemberVO member4 = dao.readMember("user11");
		if(member4 !=null )
			System.out.println("이름 : "+member4.getUsername());
		
	}	
}
