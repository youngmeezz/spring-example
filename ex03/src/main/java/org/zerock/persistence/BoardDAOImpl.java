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
	
	@Override
	public void updateViewCnt(Integer bno) throws Exception {
		session.update(namespace+".updateViewCnt",bno);				
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
		
	//삭제
	@Override
	public void delete(Integer bno) throws Exception {
		session.delete(namespace+".delete",bno);
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
	
	
}
















