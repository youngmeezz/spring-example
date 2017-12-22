package com.test.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.test.domain.TestDomain;

@Repository
public class TestDao {	
	private static final Logger logger = LoggerFactory.getLogger(TestDao.class);
	
	@Inject
	private SqlSession session;
	
	private static final String namespace = "com.test.mapper.TestMapper";
	
	@Cacheable(value="testCache")
	public String findNameById(Integer id) {
		logger.info("findNameById() : " + id);
		return session.selectOne(namespace + ".findNameById", id);
	}
	
	@Cacheable(value="testCache")
	public TestDomain findOneById(Integer id) {
		logger.info("findOneById() : " + id);
		return session.selectOne(namespace + ".findOneById", id);
	}
	
	@Cacheable(value="testCache")
	public List<TestDomain> findAllByName(String name) {
		logger.info("findAllByName() : " + name);
		return session.selectList(namespace + ".findAllByName", name);
	}
	
	// check again
	public TestDomain findOneLastSaved() {
		return session.selectOne(namespace + ".findOneByName");
	}
	
	@CacheEvict("testCache")
	public int save(String name) {
		logger.info("save() : " + name);
		return session.insert(namespace + ".save", name);
	}
	
	@CacheEvict("testCache")
	public int deleteAll() {
		logger.info("deleteAll() ");
		return session.delete(namespace + ".deleteAll");
	}
}
