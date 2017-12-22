package com.test.es;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

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

import com.test.domain.es.Access;
import com.test.domain.es.Repository;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={
		"file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/persistence-context.xml"
		})
public class NestedTest {
	@Inject ElasticsearchTemplate template;	
	private Access read1,read2;
	private Access write;
	
	@Before 
	public void setUp() {
		// read 1
		read1 = new Access();
		read1.setIp("127.0.0.1");
		read1.setId("read1");
		
		Repository repository1 = new Repository();
		repository1.setLabels(Arrays.asList("cellphone,telephone,email"));
		repository1.setIp("127.0.0.2");
		String[] values11 = new String[] {
				"010",null,"test@zaccoding.com"
		};
		String[] values12 = new String[] {
				null,"02","test@zaccoding.com"
		};
		repository1.setValues(Arrays.asList(values11,values12));
		
		Repository repository2 = new Repository();
		repository2.setLabels(Arrays.asList("cellphone,account,social"));
		repository2.setIp("127.0.0.3");
		String[] values21 = new String[] {
				"010",null,"test@zaccoding.com"
		};
		String[] values22 = new String[] {
				null,"02","test@zaccoding.com"
		};
		repository2.setValues(Arrays.asList(values21,values22));
		
		read1.setRepositories(Arrays.asList(repository1,repository2));
	}
	
	//@Test 
	public void save() {
		template.deleteIndex(Access.class);
		template.createIndex(Access.class);
		template.putMapping(Access.class);
		template.refresh(Access.class);
		
		IndexQuery indexQuery = new IndexQuery();
		indexQuery.setId(read1.getId());
		indexQuery.setObject(read1);
		System.out.println("## [index query] \n" + indexQuery.toString());
		
		String savedId = template.index(indexQuery);
		assertThat(savedId,is(read1.getId()));
	}
	
	@Test
	public void extractField() {
		QueryBuilder builder = QueryBuilders.boolQuery()
				.must(QueryBuilders.termQuery("ip", "127.0.0.1"))
				.must(QueryBuilders.nestedQuery("repositories", QueryBuilders.boolQuery()
							.must(QueryBuilders.termQuery("repositories.ip", "127.0.0.3"))
							.must(QueryBuilders.boolQuery()
									.should(QueryBuilders.termQuery("repositories.labels", "account"))
				)));
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
								.withQuery(builder)
								.withFields("repositories")
								.build();
		List<Access> list = template.queryForList(searchQuery,Access.class);
		System.out.println(list.size());
		list.forEach((k) -> {System.out.println(k);});
	}
	
	// @Test
	public void search() {
		QueryBuilder builder = QueryBuilders.boolQuery()
				.must(QueryBuilders.termQuery("ip", "127.0.0.1"))
				.must(QueryBuilders.nestedQuery("repositories", QueryBuilders.boolQuery()
							.must(QueryBuilders.termQuery("repositories.ip", "127.0.0.2"))
							.must(QueryBuilders.boolQuery()
									.should(QueryBuilders.termQuery("repositories.labels", "cellphone"))
									.should(QueryBuilders.termQuery("repositories.labels", "email"))
				)));
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
								.withQuery(builder)
								//.withIndices("data")
								//.withTypes("access")
								.withFields("repositories.values")
								.build();
		System.out.println("## [SearchQuery]\n"+searchQuery.getQuery().toString());		
		//System.out.println(template.count(searchQuery));
		List<Access> list = template.queryForList(searchQuery, Access.class);
		for(Access access : list) {
			System.out.println(access);
		}
		
		
		//assertTrue(template.count(searchQuery) == 1L);
		
		//("repositories.labels", "cellphone")
		
	}
	
	

}
