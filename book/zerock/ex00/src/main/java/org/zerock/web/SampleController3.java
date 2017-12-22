package org.zerock.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.domain.ProductVO;


/*
 * 만들어진 결과 데이터를 전달해야 하는 경우 
 */
@Controller
public class SampleController3 {
	private static final Logger logger = LoggerFactory.getLogger(SampleController3.class);
	
	@RequestMapping("/doD")
	public String doD(Model model) { //Model : 뷰에 원하는 데이터를 전달하는 일종의 컨테이너
		//make sample data
		ProductVO product = new ProductVO("SampleProduct",10000);
		logger.info("doD");
		
		/*
		 * 1)addAttribute("이름",객체) : 객체에 특별한 이름을 부여해 뷰에서 이름값을 이용하여 객체 처리
		 * 2)addAttribute(객체) : 이름을 지정하지 않는 경우에는 자동으로 저장되는 객체의 클래스명 앞 글자를 소문자로 처리한 클래스명을 이름으로 간주
		 */
		model.addAttribute(product); //뷰에서 'productVO'가 됨
		
		return "productDetail";
	}
}
