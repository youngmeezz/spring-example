package com.test.persistence;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.test.domain.TestDomain;

@Repository
public class TestDao {	
	private static final Logger logger = LoggerFactory.getLogger(TestDao.class);
	
	@Inject
	private SqlSession session;
	
	private static final String namespace = "com.test.mapper.TestMapper";
	
	@Cacheable(value="testCache",key="#result.id")
	public String findNameById(Integer id) {
		logger.info("findNameById()");
		return session.selectOne(namespace + ".findNameById", id);
	}
	
	@Cacheable(value="testCache",key="#result.id")
	public TestDomain findOneById(Integer id) {
		logger.info("findOneById()");
		return session.selectOne(namespace + ".findOneById", id);
	}
	
	@Cacheable(value="testCache")
	public TestDomain findOneByName(String name) {
		logger.info("findOneByName()");
		return session.selectOne(namespace + ".findOneByName", name);
	}
	
	@CachePut(value="testCache")
	public int save(String name) {
		logger.info("save");
		return session.insert(namespace + ".save", name);
	}
	
	public int deleteAll() {
		logger.info("deleteAll");
		return session.delete(namespace + ".deleteAll");
	}
}
