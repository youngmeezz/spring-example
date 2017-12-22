package org.zerock.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * void 리턴 타입인 경우
 * => /web/doA를 하면 /...../doA.jsp를 찾음
 * => /web/doB를 하면 ...../doB.jsp를 찾음
 */

@Controller
public class SampleController {
	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);
	
	@RequestMapping("doA")
	public void doA() {
		logger.info("doA called.....");
	}
	
	@RequestMapping("doB")
	public void doB() {
		logger.info("doB called....");
	}
	
	
}
