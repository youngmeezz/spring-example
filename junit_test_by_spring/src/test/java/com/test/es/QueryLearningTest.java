package com.test.es;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class QueryLearningTest {
	TestDomain testDomain1;
	TestDomain testDomain2;
	TestDomain testDomain3;
	TestDomain testDomain4;
	
	@Inject ElasticsearchTemplate elasticsearchTemplate;
	
	@Before public void setUp() {
		testDomain1 = new TestDomain();
		testDomain1.setAge(1);
		testDomain1.setName("domain1");
		testDomain1.setHobbies(makeListFromArrays("hobbies1","hobbies11"));
		
		testDomain2 = new TestDomain();
		testDomain2.setAge(2);
		testDomain2.setName("domain2");
		testDomain2.setHobbies(makeListFromArrays("hobbies2","hobbies22"));
		
		testDomain3 = new TestDomain();
		testDomain3.setAge(3);
		testDomain3.setName("domain3");
		testDomain3.setHobbies(makeListFromArrays("hobbies3","hobbies33"));
		
		testDomain4 = new TestDomain();
		testDomain4.setAge(4);
		testDomain4.setName("domain4");
		testDomain4.setHobbies(makeListFromArrays("hobbies4","hobbies44"));
	}
	
	/**
	 * 인덱스로 읽기
	 */
	@Test
	public void readByIndex() {
		 SearchQuery searchQuery = new NativeSearchQueryBuilder()
			      					.withQuery(QueryBuilders.matchAllQuery())
			      					.withIndices("domain")
			      					.withFields("name","age") //특정 필드만 가져오는 메소드
			      					.build();
		 List<TestDomain> list =  elasticsearchTemplate.queryForList(searchQuery, TestDomain.class);
		 long size = elasticsearchTemplate.count(searchQuery,TestDomain.class);
		 System.out.println("## [size]  "+ size);
		 displayList(list);
	}
	
	/**
	 *  페이징 처리
	 */
	//@Test
	public void readByPageAndSort() {
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
								.withQuery(QueryBuilders.matchAllQuery())
								.withIndices("domain")
								.withSort(new FieldSortBuilder("age").order(SortOrder.ASC))
								.withPageable(new PageRequest(0,2))
								.build();
		List<TestDomain> list =  elasticsearchTemplate.queryForList(searchQuery, TestDomain.class);
		displayList(list);		
	}
	
	@Test
	public void searchByFiledValue() {
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
								//.withQuery(QueryBuilders.termQuery("name","domain1")) // where name = 'domain1'
								//.withQuery(QueryBuilders.wildcardQuery("name","*domain*")) // like name = '%domain1%'
								.withQuery(QueryBuilders.queryStringQuery("*hobiies*"))								
								.build();		
		List<TestDomain> list =  elasticsearchTemplate.queryForList(searchQuery, TestDomain.class);		
		displayList(list);				
	}
	
	/**
	 * 전체 삭제
	 * 저장 & 검색
	 */
	//@Test
	public void save() {
		// delete all		
		assertTrue(deleteAll());
				
		IndexQuery indexQuery = new IndexQuery();
		
		// save 1 & find by id & assert
		indexQuery.setObject(testDomain1);
		String id1 = elasticsearchTemplate.index(indexQuery);
		System.out.println("## [id1] : " + id1);
		assertThat(testDomain1,is(findById(id1)) );
		
		
		// save2 & find by id & assert
		indexQuery.setObject(testDomain2);
		String id2 = elasticsearchTemplate.index(indexQuery);
		System.out.println("## [id2] : " + id2);
		assertThat(testDomain2,is(findById(id2)) );
		
		// save3 & find by id & assert
		indexQuery.setObject(testDomain3);
		String id3 = elasticsearchTemplate.index(indexQuery);
		System.out.println("## [id3] : " + id3);
		assertThat(testDomain3,is(findById(id3)) );
		
		// save4 & find by id & assert
		indexQuery.setObject(testDomain4);
		String id4 = elasticsearchTemplate.index(indexQuery);
		System.out.println("## [id4] : " + id4);
		assertThat(testDomain4,is(findById(id4)));
	}
	
	/**
	 * ID로 검색
	 */
	private TestDomain findById(String id) {
		GetQuery getQuery = new GetQuery();
		getQuery.setId(id);
		return elasticsearchTemplate.queryForObject(getQuery, TestDomain.class);
	}
	
	private boolean deleteAll() {
		elasticsearchTemplate.deleteIndex(TestDomain.class);
		return elasticsearchTemplate.indexExists(TestDomain.class) == false;
	}
	
	public void displayList(List<TestDomain> list) {
		if(list == null) {
			System.out.println("## [list is null]");
			return;
		}
		else if(list.isEmpty()) {
			System.out.println("## [list is empty");
		}
			
		for(int i=0; i<list.size(); i++) {
			System.out.println("## [domain" + i + "] " + list.get(i));
		}
	}
	
	private List<String> makeListFromArrays(String ... strings) {
		List<String> list = new ArrayList<>();		
		for(String string : strings) {
			list.add(string);
		} 			
		return list;
	}	

}
