package org.zerock.controller;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.zerock.domain.ReplyVO;
import org.zerock.persistence.ReplyDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class ReplyDAOTest {
	private static Logger logger = LoggerFactory.getLogger(ReplyDAOTest.class);
	
	@Inject
	private ReplyDAO dao;
	
	@Test
	public void testCreate() throws Exception {
		ReplyVO vo = new ReplyVO();
		vo.setBno(6127);
		vo.setReplyer("user00");
		vo.setReplytext("content");
		
		dao.create(vo);
		
	}
	

}
