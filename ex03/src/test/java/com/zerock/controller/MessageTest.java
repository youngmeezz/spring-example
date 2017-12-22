package com.zerock.controller;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.zerock.domain.MessageVO;
import org.zerock.persistence.MessageDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class MessageTest {
	private static Logger logger = LoggerFactory.getLogger(MessageTest.class);
	
	@Inject
	private MessageDAO dao;
	
	@Test
	public void testCreate() throws Exception {	
		MessageVO vo = new MessageVO();
		vo.setTargetid("user01");
		vo.setSender("user00");
		vo.setMessage("Good Luck!!");
		dao.create(vo);		
	}

}
