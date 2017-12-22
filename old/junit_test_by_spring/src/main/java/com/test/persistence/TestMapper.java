package com.test.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.cache.annotation.CacheEvict;
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
	
	@Select("select name from tbl_test_domain where id = #{id}")
	public String findNameById(@Param("id") int id );
	
	@Select("select * from tbl_test_domain where id = #{id}")
	public TestDomain findOneById(@Param("id") int id);
	
	@Cacheable(value="testCache")
	@Select("select * from tbl_test_domain where name=#{name}")
	public List<TestDomain> findAllByName(@Param("name") String name);
	
	@CacheEvict("testCache")
	@Insert("insert into tbl_test_domain (id,name) values(test_domain_id_seq.nextval,#{name} )")
	public int save(@Param("name") String name);
	
	@CacheEvict("testCache")
	@Delete("delete from tbl_test_domain where id > 0")
	public int deleteAll();	
	
	// for test	
	@Update("drop sequence test_domain_id_seq")
	public void dropSequence();
	@Update("create sequence test_domain_id_seq")
	public void createSequence();	
}










