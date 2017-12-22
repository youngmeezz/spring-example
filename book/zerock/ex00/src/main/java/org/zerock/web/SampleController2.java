package org.zerock.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


/*
 * String이 리턴 타입인 경우
 * 
 * =>/web/doC를 요청하면 ..../result.jsp를 찾음(doC의 반환이 "return"인 경우)
 * 
 */

@Controller
public class SampleController2 {
	
	private static final Logger logger = LoggerFactory.getLogger(SampleController2.class);
	
	@RequestMapping("doC")
	public String doC(@ModelAttribute("msg") String msg) {
		logger.info("doC called...");
		msg = "byby";
		return "result";
	}
	
	

}
