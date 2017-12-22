package org.zerock.controller;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.zerock.domain.BoardVO;
import org.zerock.domain.SearchCriteria;
import org.zerock.persistence.BoardDAO;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class BoardDAOTest {	
	private static Logger logger = LoggerFactory.getLogger(BoardDAOTest.class);
	
	@Inject
	private BoardDAO dao;
	
//	@Test
//	public void testCreate()throws Exception {
//		BoardVO board = new BoardVO();
//		board.setTitle("새로운 글을 넣음");
//		board.setContent("새로운 글 콘텐츠");
//		board.setWriter("user00");
//		dao.create(board);		
//	}
//	
//	@Test
//	public void testRead()throws Exception {
//		logger.info(dao.read(5).toString());		
//	}
//	
//	@Test
//	public void testUpdate()throws Exception {
//		BoardVO board = new BoardVO();
//		board.setBno(5);
//		board.setTitle("수정된 글");
//		board.setContent("수정된 콘텐츠");
//		dao.update(board);		
//	}
//	
//	@Test
//	public void testDelete()throws Exception {
//		dao.delete(5);
//	}
//	@Test
//	public void testListPage()throws Exception {
////		List<BoardVO> list = dao.listPage(3);
////		for(BoardVO boardVO : list)
////			logger.info(boardVO.getBno()+" : "+boardVO.getTitle());
//		Criteria cri = new Criteria();
//		cri.setPage(2);
//		cri.setPerPageNum(20);
//		List<BoardVO> boardList = dao.listPage(cri);
//		int i = 1;
//		for(BoardVO boardVO : boardList) { 
//			logger.info(i+"번쨰,"+boardVO.getBno()+" : "+boardVO.getTitle());
//			i++;
//		}
//		//+MySQL 과 비교
//		//select bno,title from tbl_board where bno > 0 order by bno desc limit 20,20;
//		
//	}
//	@Test
//	public void testURI() throws Exception {
////		UriComponents uriComponents = 
////				UriComponentsBuilder.newInstance()
////				.path("/board/read")
////				.queryParam("bno", 12)
////				.queryParam("perPageNum", 20)
////				.build();
//		
//		//미리 필요한 경로를 지정해 두고, '{module}'와 같은 경로를 'board','{page}'를 'read'로 변경 가능
//		UriComponents uriComponents = 
//				UriComponentsBuilder.newInstance()
//				.path("/{module}/{page}")
//				.queryParam("bno", 12)
//				.queryParam("perPageNum", 20)
//				.build()
//				.expand("board","read")
//				.encode();
//		logger.info("/board/read?bno=12&paerPageNum=20");
//		logger.info(uriComponents.toString());
//	}
	
	@Test
	public void testDynamic1() throws Exception {
		SearchCriteria cri = new SearchCriteria();
		cri.setPage(1);
		cri.setSearchType("t");
		cri.setKeyword("hihititle");
		
		logger.info("===================");
		List<BoardVO> list = dao.listSearch(cri);
		for(BoardVO boardVO : list) {
			logger.info(boardVO.getBno()+": "+boardVO.getTitle());
		}
		logger.info("===================");
		logger.info("count : "+list.size());		
	}
	
	
}










