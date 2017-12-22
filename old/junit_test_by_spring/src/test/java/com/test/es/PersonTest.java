package com.test.es;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import javax.inject.Inject;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.test.domain.es.Person;

/**
 * 1. index test
 * @author zaccoding
 * @date 2017. 7. 26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={
		"file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/persistence-context.xml"
		})
public class PersonTest {	
	@Inject ElasticsearchTemplate template;	
	private Person person1;
	private Person person2;
	
	@Before public void setUp() {
		//deleteAndCreateAndRefresh();
	}
	
	//@Test 
	public void index() {
		template.deleteIndex(Person.class);
		template.createIndex(Person.class);
		template.putMapping(Person.class);
		template.refresh(Person.class);
		
		IndexQuery indexQuery = new IndexQuery();
		indexQuery.setId(person1.getId());
		indexQuery.setObject(person1);
		
		String retString = template.index(indexQuery);
		System.out.println(retString);
	}
	
	@Test 
	public void saveAndSearch() {
		// find : "age" = "22", has "hobby" = "coding" ==> count() == 2L
		QueryBuilder builder = QueryBuilders.boolQuery()
					.must(QueryBuilders.termQuery("age",22))
					//.must(QueryBuilders.termQuery("hobbies", "coding"));
					.must(QueryBuilders.termQuery("hobbies", "music"));
		
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
								.withQuery(builder)
								.withIndices("person")
								//.withTypes("user")
								.build();
		System.out.println("## [search query]\n" + searchQuery.getQuery().toString());
		
		System.out.println(template.count(searchQuery));
		//assertThat(template.count(searchQuery), is(2L));
	}
	
	private void deleteAndCreateAndRefresh() {
		template.deleteIndex(Person.class);
		template.createIndex(Person.class);
		template.putMapping(Person.class);
		template.refresh(Person.class);
		
		person1 = new Person();
		person1.setId("hivaid1");
		person1.setName("hivaname");
		person1.setAge(22);
		person1.setHobbies(Arrays.asList("music,movie,coding"));
		
		person2 = new Person();
		person2.setId("hivaid2");
		person2.setName("hivaname2");
		person2.setAge(22);
		person2.setHobbies(Arrays.asList("dance,coding"));
		
		IndexQuery indexQuery1 = new IndexQuery();
		indexQuery1.setId(person1.getId());
		indexQuery1.setObject(person1);
		
		IndexQuery indexQuery2 = new IndexQuery();
		indexQuery2.setId(person2.getId());
		indexQuery2.setObject(person2);
		
		template.bulkIndex(Arrays.asList(indexQuery1,indexQuery2));	
	}
	
	
	
	
	

}
