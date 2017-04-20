package org.zerock.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.SearchCriteria;
import org.zerock.persistence.BoardDAO;

@Service
public class BoardServiceImpl implements BoardService {
	@Inject
	private BoardDAO dao;

	@Transactional
	@Override
	public void regist(BoardVO board) throws Exception {
		dao.create(board);
		String[] files = board.getFiles();
		if (files == null)
			return;
		for (String fileName : files) {
			dao.addAttach(fileName);
		}
	}
	
	
	@Override
	public List<BoardVO> listSearchCriteria(SearchCriteria cri) throws Exception {
		return dao.listSearch(cri);
	}

	@Override
	public int listSearchCount(SearchCriteria cri) throws Exception {
		return dao.listSearchCount(cri);
	}

	@Transactional
	@Override
	public void modify(BoardVO board) throws Exception {
		// 기존 게시글 업데이트
		dao.update(board);

		// 기존 첨부 파일 목록 삭제
		Integer bno = board.getBno();
		dao.deleteAttach(bno);

		// 새로운 첨부파일 정보 입력
		String[] files = board.getFiles();
		if (files == null)
			return;

		for (String fileName : files) {
			dao.replaceAttach(fileName, bno);
		}
	}

	@Transactional(isolation = Isolation.READ_COMMITTED) // 커밋된 데이터에 대해 읽기 허용
	@Override
	public BoardVO read(Integer bno) throws Exception {
		dao.updateViewCnt(bno);
		return dao.read(bno);
	}

	@Transactional
	@Override
	public void remove(Integer bno) throws Exception {
		dao.deleteAttach(bno);
		dao.delete(bno);
	}

	

	@Override
	public List<BoardVO> listAll() throws Exception {
		return dao.listAll();
	}

	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		return dao.listPage(cri);
	}

	@Override
	public int listCountCriteria(Criteria cri) throws Exception {
		return dao.countPaging(cri);
	}

	@Override
	public List<String> getAttach(Integer bno) throws Exception {
		return dao.getAttach(bno);
	}
}
