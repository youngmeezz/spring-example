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
import org.zerock.domain.PageMaker;
import org.zerock.domain.SearchCriteria;
import org.zerock.service.BoardService;

@Controller
@RequestMapping("/sboard/*")
public class SearchBoardController {	
	//variables
	private static final Logger logger = LoggerFactory.getLogger(SearchBoardController.class);	
	@Inject
	private BoardService service;	
	
	//methods	
	///////////////////////
	// 검색 LIST
	///////////////////////	
	@RequestMapping(value="/list",method = RequestMethod.GET)
	public void listPage(@ModelAttribute("cri") SearchCriteria cri,Model model) throws Exception {
		logger.info(cri.toString());
		
		model.addAttribute("list", service.listSearchCriteria(cri));
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.listSearchCount(cri));
		
		model.addAttribute("pageMaker",pageMaker);		
		//ModelAttribute("cri")는 요청(request)시 'cri'이름의 파라미터를 SearchCriteria 타입으로 처리 & 뷰에 전달
		//service를 통해서 데이터를 얻은 "list"나 "pageMaker"는 스프링 제공 Model 클래스로 전달

		
//		PageMaker pageMaker = new PageMaker();
//		pageMaker.setCri(cri);
//		pageMaker.setTotalCount(service.listSearchCount(cri));
//		
//		//model의 addAttribute는 return이 this라서 연속으로 써도됨
//		model.addAttribute("list",service.listSearchCriteria(cri))
//			.addAttribute("pageMaker",pageMaker);
	}
	
	///////////////////////
	// 게시글 조회
	///////////////////////
	@RequestMapping(value="/readPage",method=RequestMethod.GET)
	public void read(@RequestParam("bno") int bno,@ModelAttribute("cri") SearchCriteria cri,Model model) throws Exception {
		model.addAttribute(service.read(bno));
	}
	
	///////////////////////
	// 게시글 삭제
	///////////////////////
	@RequestMapping(value="/removePage",method=RequestMethod.POST)
	public String remove(@RequestParam("bno") int bno,SearchCriteria cri,RedirectAttributes rttr) throws Exception {
		service.remove(bno);
		
		rttr.addAttribute("page",cri.getPage());
		rttr.addAttribute("perPageNum",cri.getPerPageNum());
		rttr.addAttribute("searchType",cri.getSearchType());
		rttr.addAttribute("keyword",cri.getKeyword());
		
		rttr.addFlashAttribute("msg", "SUCCESS");
		
		return "redirect:/sboard/list";		
	}
	
	///////////////////////
	// 게시글 수정
	///////////////////////	
	//GET
	@RequestMapping(value = "/modifyPage",method=RequestMethod.GET)
	public void modifyPagingGET(int bno,@ModelAttribute("cri") SearchCriteria cri,Model model) throws Exception {
		model.addAttribute(service.read(bno));
	}	
	
	//POST
	@RequestMapping(value = "/modifyPage",method=RequestMethod.POST)
	public String modifyPagingPOST(BoardVO board,SearchCriteria cri,RedirectAttributes rttr) throws Exception {
		logger.info(cri.toString());
	    service.modify(board);

	    rttr.addAttribute("page", cri.getPage());
	    rttr.addAttribute("perPageNum", cri.getPerPageNum());
	    rttr.addAttribute("searchType", cri.getSearchType());
	    rttr.addAttribute("keyword", cri.getKeyword());

	    rttr.addFlashAttribute("msg", "SUCCESS");

	    logger.info(rttr.toString());

	    return "redirect:/sboard/list";
	}	

	///////////////////////
	// 게시글 등록
	///////////////////////
	//GET
	@RequestMapping(value="/register",method=RequestMethod.GET)
	public void registGET() throws Exception {
		logger.info("regist get...");
	}
	
	//POST
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public String registPOST(BoardVO board,RedirectAttributes rttr) throws Exception {
		logger.info("regist post...");
		logger.info(board.toString());
		
		service.regist(board);
		
		rttr.addFlashAttribute("msg", "SUCCESS");
		
		return "redirect:/sboard/list";
	}

}
















