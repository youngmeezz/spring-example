package org.zerock.persistence;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

public interface SampleMapper {

	@Select("select now()")
	public String getTime();
	
	//@Param을 이용한 다중 파라미터 처리
	@Select("select uname from tbl_user where uid= #{id} and upw= #{pw}")
	public String getEmail(
			@Param("id") String uid,
			@Param("pw") String upw);	
	
	
	//Mapper 인터페이스와 XML을 같이 활용하기
	// 1. Mapper 인터페이스의 이름과 XML Mapper의 네임스페이스 일치
	// 2. XML의 id는 Mapper 인터페이스의 메소드 이름과 동일하게 작성
	public String getUserName(
			@Param("id") String uid,
			@Param("pw") String upw);
	
	
	//Dynamic SQL 과 @SelectProvider	
	@SelectProvider(type=SampleProvider.class,method="searchUserName")
	public String search(
			@Param("type") String type,
			@Param("keyword")String keyword);
	
	
	
	

}
