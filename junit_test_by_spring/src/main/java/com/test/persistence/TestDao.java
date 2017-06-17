package com.test.persistence;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class TestDao {	
	@Inject
	private SqlSession session;
	
	private static final String namespace = "com.test.mapper.TestMapper";
	
	
	
}
