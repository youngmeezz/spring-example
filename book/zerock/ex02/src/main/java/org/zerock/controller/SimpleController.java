package org.zerock.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.SampleVO;

/*
 * RestController의 용도
 *  -> JSP와 같은 뷰를 만들어 내지 않는 대신에 데이터 자체를 반환하는데,
 *  -> 주로 단순 문자열,JSON,XML 등으로 나눌 수 있음
 */
@RestController
@RequestMapping("/sample")
public class SimpleController {
	
	@RequestMapping("/hello")
	public String sayHello() {
		return "Hello World"; //단순히 Hello World라는 문자열 반환.웹브라우저에서 소스코드 보기 하면 'text/html'타입의 데이터 전송
	}
	
	
	/*
	 * 개발자 도구 -> network -> Response Headers 의 Content-Type: application/json;charset=UTF-8
	 */
	@RequestMapping("/sendVO")
	public SampleVO sendVO() {
		SampleVO vo = new SampleVO();
		vo.setFirstName("길동");
		vo.setLastName("홍");
		vo.setNo(123);		
		return vo;
	}
	
	@RequestMapping("/sendList")
	public List<SampleVO> sendList() {
		List<SampleVO> list = new ArrayList<>();
		for(int i=0;i<10;i++) {
			SampleVO vo = new SampleVO();
			vo.setFirstName("길동");
			vo.setLastName("홍");
			vo.setNo(i);
			list.add(vo);
		}		
		return list;
	}
	
	@RequestMapping("/sendMap")
	public Map<Integer,SampleVO> sendMap() {
		Map<Integer,SampleVO> map = new HashMap<>();
		for(int i=0;i<10;i++) {
			SampleVO vo = new SampleVO();
			vo.setFirstName("길동");
			vo.setLastName("홍");
			vo.setNo(i);
			map.put(i, vo);
		}		
		return map;
		/*
		 * '키:값'형태로 출력
		 */
	}
	
	
	//예외 대신에 ResponseEntity를 이용해서 사용자에게 정보를 전달하는 예제
	@RequestMapping("/sendErrorAuth")
	public ResponseEntity<Void> sendListAuth() {
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		//HTTP의 상태코드(Status Code)가 400으로 전송됨
	}
	
	//결과 데이터와 HTTP의 상태 코드를 같이 사용해야 하는 경우
	@RequestMapping("/sendErrorNot")
	public ResponseEntity<List<SampleVO>> sendListNot() {
		List<SampleVO> list = new ArrayList<>();
		for(int i=0;i<10;i++) {
			SampleVO vo = new SampleVO();
			vo.setFirstName("길동");
			vo.setLastName("홍");
			vo.setNo(i);
			list.add(vo);
		}		
		return new ResponseEntity<>(list,HttpStatus.NOT_FOUND);
		//list결과 데이터가 출력 & 상태코드는 404
	}	
}















