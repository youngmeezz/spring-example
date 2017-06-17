package com.test.persistence;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import com.test.domain.TestDomain;

/**
 * Test Mapper 
 * 
 * @author zaccoding
 * 
 */
public interface TestMapper {
	
	@Cacheable(value="testCache",key="#result.id")
	@Select("select name from tbl_test_domain where id = #{id}")
	public String findNameById(@Param("id") int id );
	
	//@Cacheable(value="testCache",key="#result.id")
	@Select("select * from tbl_test_domain where id = #{id}")
	public TestDomain findOneById(@Param("id") int id);
	
	//@Cacheable(value="testCache",key="#result.id")
	@Select("select * from tbl_test_domain where name=#{name}")
	public TestDomain findOneByName(@Param("name") String name);
	
	@CachePut("testCache")
	@Insert("insert into tbl_test_domain (id,name) values(test_domain_id_seq.nextval,#{name} )")
	public int save(@Param("name") String name);
	
	@Delete("delete from tbl_test_domain where id > 0")
	public int deleteAll();
	

}
