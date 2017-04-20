package org.zerock.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageMaker;
import org.zerock.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Inject
	private BoardService service;
	
	//////////////
	// 게시글 등록 
	//////////////
	//GET
	@RequestMapping(value = "/register",method=RequestMethod.GET)
	public void registerGET(BoardVO board,Model model) throws Exception {
		logger.info("register get.....");
	}
	//POST
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public String registerPOST(BoardVO board,Model model,RedirectAttributes rttr) throws Exception {
		logger.info("register post.....");
		logger.info(board.toString());
		
		service.regist(board);
		//model.addAttribute("result","success"); //URI에 포함되서 /board/viewPage?result=success 로 전달
		rttr.addFlashAttribute("msg","SUCCESS");
		//return "/board/success"; ///WEB-INF/views/board/success.jsp -> 새로 고침 시 다시 POST방식으로 데이터전달 -> 도배 가능
		return "redirect:/board/listAll";
	}

	
	//////////////
	// 게시글 조회
	//////////////
//	//@RequestParam("bno") == request.getParameter("bno")
//	//차이점 : 문자열,숫자,날짜 등의 형 변환이 가능.
//	@RequestMapping(value="/read",method=RequestMethod.GET)
//	public void read(@RequestParam("bno") int bno,Model model) throws Exception { 
//		model.addAttribute(service.read(bno)); //이름이 없으면,자동으로 클래스 이름의 소문자로 시작해서 사용. i.e -> BoardVO -> boardVO
//	}
	
	@RequestMapping(value="/readPage",method=RequestMethod.GET)
	public void read(@RequestParam("bno") int bno,
				@ModelAttribute("cri") Criteria cri,
				Model model) throws Exception { 
		model.addAttribute(service.read(bno)); //이름이 없으면,자동으로 클래스 이름의 소문자로 시작해서 사용. i.e -> BoardVO -> boardVO
	}
	
	//////////////
	// 게시글 삭제
	//////////////
//	//삭제 + 1페이지로
//	@RequestMapping(value="/remove",method=RequestMethod.POST)
//	public String remove(@RequestParam("bno") int bno,RedirectAttributes rttr) throws Exception {
//		service.remove(bno);
//		
//		rttr.addFlashAttribute("msg","SUCCESS"); //임시로 사용하는 데이터 ,query스트링에 포함X,F5하면 존재X
//		
//		return "redirect:/board/listAll";
//	}
	//삭제 + 기존 페이지
	@RequestMapping(value="/removePage",method=RequestMethod.POST)
	public String remove(@RequestParam("bno") int bno, Criteria cri,RedirectAttributes rttr) throws Exception {
		service.remove(bno);
		logger.info("/removePage....");
		logger.info(cri.toString());
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());		
		rttr.addFlashAttribute("msg","SUCCESS");		
		return "redirect:/board/listPage";
	}
	
	//////////////
	// 게시글 수정
	//////////////
	//GET
	@RequestMapping(value="/modify",method=RequestMethod.GET)
	public void modifyGET(int bno,Model model) throws Exception {
		model.addAttribute(service.read(bno));
	}	
	//
	@RequestMapping(value="/modifyPage",method=RequestMethod.GET)
	public void modifyPagingGET(int bno,@ModelAttribute("cri") Criteria cri,Model model) throws Exception {
		model.addAttribute(service.read(bno));
		
	}
	
	//POST
	@RequestMapping(value="/modify",method=RequestMethod.POST)
	public String modifyPost(BoardVO board,RedirectAttributes rttr) throws Exception {
		logger.info("mod post....");
		
		service.modify(board);
		rttr.addFlashAttribute("msg","SUCCESS");
		
		return "redirect:/board/listAll";
	}
	
	@RequestMapping(value="/modifyPage",method=RequestMethod.POST)
	public String modifyPagingPost(BoardVO board,@ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) throws Exception {
				
		service.modify(board);
		rttr.addAttribute("page",cri.getPage());
		rttr.addAttribute("perPageNum",cri.getPerPageNum());
		rttr.addFlashAttribute("msg","SUCCESS");
		
		
		return "redirect:/board/listPage";
	}
	
	
	//////////////
	// 게시글 목록
	//////////////
	
//	//전체 리스트 출력
//	@RequestMapping(value="/listAll",method=RequestMethod.GET)
//	public void listAll(Model model) throws Exception {
//		logger.info("show all list....");
//		model.addAttribute("list",service.listAll());
//	}		

	//페이징처리
	@RequestMapping(value="/listCri",method=RequestMethod.GET)
	public void listAll(Criteria cri,Model model) throws Exception {
		logger.info("show list Page with Criteria...");		
		model.addAttribute("list",service.listCriteria(cri));
	}	
	
	//페이징처리+query string
	@RequestMapping(value="/listPage",method=RequestMethod.GET)
	public void listPage(@ModelAttribute("cri") Criteria cri,Model model) throws Exception {
		logger.info(cri.toString());
		
		model.addAttribute("list", service.listCriteria(cri));		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		//pageMaker.setTotalCount(131);
		pageMaker.setTotalCount(service.listCountCriteria(cri));
		model.addAttribute("pageMaker", pageMaker);				
	}
}










