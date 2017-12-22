package org.zerock.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageMaker;
import org.zerock.domain.ReplyVO;
import org.zerock.service.ReplyService;

@RestController
@RequestMapping("/replies")
public class ReplyController {
	@Inject
	private ReplyService service;
	
	//////////////////
	// 페이징 처리
	//////////////////
	@RequestMapping(value="/{bno}/{page}",method=RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> listPage(
						@PathVariable("bno") Integer bno,
						@PathVariable("page") Integer page) {		
		ResponseEntity<Map<String,Object>> entity = null;
		try {
			Criteria cri = new Criteria();
			cri.setPage(page);
			
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(cri);
			
			//Ajax로 호출 될 것이므로, Model을 사용 못함 => 전달해야 하는 데이터들을 담기 위해 Map타입의 객체를 별도로 생성
			Map<String,Object> map = new HashMap<>();
			List<ReplyVO> list = service.listReplyPage(bno, cri);
			
			map.put("list",list);
			
			int replyCount = service.count(bno);
			pageMaker.setTotalCount(replyCount);
			
			map.put("pageMaker", pageMaker);
			
			entity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}		
		return entity;
	}

	
	//////////////////
	//한 게시물의 전체 댓글 조회
	//////////////////
	@RequestMapping(value="/all/{bno}",method=RequestMethod.GET) //{bno}는 @PathVariable에서 bno로 활용
	public ResponseEntity<List<ReplyVO>> list(@PathVariable("bno") Integer bno) {
							//@PathVariable() : URI의 경로에서 원하는 데이터를 추출하는 용도로 사용
		System.out.println("ReplyController의 list 실행");
		ResponseEntity<List<ReplyVO>> entity = null;
		try {
			entity = new ResponseEntity<>(service.listReply(bno),HttpStatus.OK);			
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}	
	
	//////////////////
	// 댓글 등록
	//////////////////
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<String> register(@RequestBody ReplyVO vo) {
		// @RequestBody : 전송된 JSON 데이터를 객체로 변환해주는 애노테이션(@ModelAttribute와 유사 BUT
		// JSON에서 사용)
		System.out.println("ReplyController의 register 실행");
		ResponseEntity<String> entity = null;
		try {
			service.addReply(vo);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST); // 실패시 message + 400
		}
		return entity;
	}
	
	//////////////////
	// 수정처리
	//////////////////
	//일반적으로 전체 데이터를 수정하는 경우에는 PUT을 이용, 일부의 데이터를 수정하는 경우에는 PATCH를 이용
	@RequestMapping(value="/{rno}",method={RequestMethod.PUT,RequestMethod.PATCH})
	public ResponseEntity<String> update(@PathVariable("rno") Integer rno,
										@RequestBody ReplyVO vo) {
		System.out.println("ReplyController의 update 실행");
		ResponseEntity<String> entity = null;
		try {
			vo.setRno(rno);
			service.modifyReply(vo);			
			
			entity = new ResponseEntity<String>("SUCCESS",HttpStatus.OK);			
		} catch(Exception e) {
			entity = new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	//////////////////
	// 댓글 삭제
	//////////////////
	@RequestMapping(value="/{rno}",method=RequestMethod.DELETE)
	public ResponseEntity<String> remove(@PathVariable("rno") Integer rno) {
		System.out.println("ReplyController의 remove 실행");
		ResponseEntity<String> entity = null;
		try {
			service.removeReply(rno);
			entity = new ResponseEntity<>("SUCCESS",HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
}











