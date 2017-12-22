package org.zerock.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zerock.domain.ProductVO;

/*
 * JSON 데이터를 생성하는 경우
 */
@Controller
public class SampleController5 {
	
	@RequestMapping("/doJSON")
	public @ResponseBody ProductVO doJSON() {
		ProductVO vo = new ProductVO("샘플상품",30000);
		return vo;
	}
}

/*
 * {"name":"샘플상품","price":30000.0}
 */
