package org.zerock.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.SearchCriteria;

@Repository
public class BoardDAOImpl implements BoardDAO {	
	@Inject
	private SqlSession session;
	private static String namespace = "org.zerock.mapper.BoardMapper";	
	//게시글 삭제
	@Override
	public void delete(Integer bno) throws Exception {
		session.delete(namespace + ".delete", bno);
	}

	//특정 게시물 번호에 속하는 모든 첨부파일 삭제
	@Override
	public void deleteAttach(Integer bno) throws Exception {
		session.delete(namespace+".deleteAttach",bno);
	}

	@Override
	public List<String> getAttach(Integer bno) throws Exception {
		return session.selectList(namespace + ".getAttach", bno);
	}	
	
	
	//목록조회(페이징처리,검색처리)
	@Override
	public List<BoardVO> listSearch(SearchCriteria cri) throws Exception {
		return session.selectList(namespace+".listSearch",cri);		
	}
	@Override
	public int listSearchCount(SearchCriteria cri) throws Exception {
		return session.selectOne(namespace+".listSearchCount",cri);
	}	
	
	//수정
	@Override
	public void update(BoardVO vo) throws Exception {
		session.update(namespace+".update",vo);
	}

		
	//조회
	@Override
	public BoardVO read(Integer bno) throws Exception {
		return session.selectOne(namespace+".read",bno);
	}
		
	
	//등록
	@Override
	public void create(BoardVO vo) throws Exception {
		session.insert(namespace+".create",vo);		
	}
	
	//목록 조회(페이징X,검색X)
	@Override
	public List<BoardVO> listAll() throws Exception {
		return session.selectList(namespace+".listAll");
	}

	@Override
	public List<BoardVO> listPage(Criteria cri) throws Exception {		
		return session.selectList(namespace+".listPage",cri);	
	}
	@Override
	public int countPaging(Criteria cri) throws Exception {		
		return session.selectOne(namespace+".countPaging",cri);
	}
	@Override
	public void updateReplyCnt(Integer bno, int amount) throws Exception {
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("bno",bno);
		paramMap.put("amount",amount);
		session.update(namespace+".updateReplyCnt",paramMap);		
	}
	
	@Override
	public void updateViewCnt(Integer bno) throws Exception {
		session.update(namespace+".updateViewCnt",bno);				
	}
	@Override
	public void addAttach(String fullName) throws Exception {
		session.insert(namespace+".addAttach", fullName);
	}
	
	
	//수정된 상태의 파일 이름과 ,이미 등록되어 있는 게시물의 번호가 필요
	@Override
	public void replaceAttach(String fullName, Integer bno) throws Exception {
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("fullName",fullName);
		paramMap.put("bno",bno);
		
		session.insert(namespace+".replaceAttach",paramMap);
	}
}
















