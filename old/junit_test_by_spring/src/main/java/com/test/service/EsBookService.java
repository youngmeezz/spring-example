package com.test.service;

import java.util.Map;

import javax.inject.Inject;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.test.domain.EsBook;
import com.test.repository.es.EsBookRepository;

@Service
public class EsBookService {
	private static final Logger logger = LoggerFactory.getLogger(EsBookService.class);
	@Inject
	EsBookRepository repository;
	
	/**
	 * save book
	 * @author zaccoding
	 * @date 2017. 7. 23.
	 * @param book
	 * @return
	 */
	public EsBook save(EsBook book) {		
		return repository.save(book);
	}
	
	/**
	 * find by id
	 * @author zaccoding
	 * @date 2017. 7. 23.
	 * @param id
	 * @return
	 */
	public EsBook findById(String id) {		
		return repository.findOne(id);
	}
	
	/**
	 * find all
	 * 
	 * @author zaccoding
	 * @date 2017. 7. 23.
	 * @return
	 */
	public Iterable<EsBook> findAll() {
		return repository.findAll();
	}
	
	/**
	 * find all count
	 * 
	 * @author zaccoding
	 * @date 2017. 7. 23.
	 * @return
	 */
	public long count() {
		return repository.count();
	}
	
	/**
	 * find by authors
	 * 
	 * @author zaccoding
	 * @date 2017. 7. 23.
	 * @param authorName
	 * @return
	 */
	public Page<EsBook> findAllByAuthorName(String authorName) {
		// successes
		//final QueryBuilder builder = QueryBuilders.matchQuery("author.name", authorName);		
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
								.withQuery(QueryBuilders.termQuery("author.name", authorName))
								//.withQuery(builder)
								.build();
		logger.info("## [findAllByAuthorName] query : " +searchQuery.getQuery().toString());
		return repository.search(searchQuery);
	}
	
	/**
	 * find all with sort
	 * 
	 * @author zaccoding
	 * @date 2017. 7. 23.
	 * @param filedName
	 * @param sortOrder
	 * @return
	 */
	public Page<EsBook> findAllBySort(String filedName, SortOrder sortOrder) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
								.withQuery(QueryBuilders.matchAllQuery())
								.withSort(new FieldSortBuilder(filedName).order(sortOrder))
								.build();
		
		return repository.search(searchQuery);
	}
	
	/**
	 * find all with search map
	 * 
	 * @author zaccoding
	 * @date 2017. 7. 23.
	 * @param searchMap
	 * @return
	 */
	public Page<EsBook> findAllByMap(Map<String,Object> searchMap) {
		if(searchMap == null || searchMap.size()==0) {
			return repository.search(new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchAllQuery()).build());
		}
		BoolQueryBuilder builder = QueryBuilders.boolQuery();
		searchMap.forEach((k,v)->{
			builder.must(QueryBuilders.termQuery(k, v));
		});
		
		logger.info("## [findAllByMap] query : " + builder.toString());
		
		return repository.search(builder, new PageRequest(0,50));
//		
//		SearchQuery searchQuery = new NativeSearchQueryBuilder()
//								.withQuery(builder)
//								.build();
//		return repository.search(searchQuery);
	}
	
}












