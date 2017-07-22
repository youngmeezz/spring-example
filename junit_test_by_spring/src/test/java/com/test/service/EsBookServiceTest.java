package com.test.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.elasticsearch.search.sort.SortOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.test.domain.EsAuthor;
import com.test.domain.EsBook;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={
		"file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/persistence-context.xml"
		})
public class EsBookServiceTest {
	@Inject private EsBookService service;
	@Inject ElasticsearchOperations elasticsearchTemplate;
	// authors
	private EsAuthor author1,author2;	
	// books
	private EsBook book1,book2,book3,book4,book5,book6;
	
	@Before public void setUp() {
		elasticsearchTemplate.deleteIndex(EsBook.class);
		elasticsearchTemplate.createIndex(EsBook.class);
		elasticsearchTemplate.putMapping(EsBook.class);
		elasticsearchTemplate.refresh(EsBook.class);
		createEntities();
		saveAndFindById();
	}
	
	// @Test
	public void findByAuthorName() {
		Page<EsBook> page = service.findAllByAuthorName(author1.getName());
		System.out.println("## [ find by author name ] ");
		displayPageElts(page);		
		System.out.println("## --");
	}
	
	// @Test
	public void findAllWithSort() {
		Page<EsBook> page = service.findAllBySort("releaseDate", SortOrder.DESC);
		System.out.println("## [ find all with sort ] ");
		displayPageElts(page);
		System.out.println("## --");		
	}
	
	@Test
	public void findAllWithSearchMap() {
		Map<String,Object> searchMap = new HashMap<>();
		searchMap.put("author.name", author1.getName());
		searchMap.put("title", "book1");
		
		Page<EsBook> page = service.findAllByMap(searchMap);
		System.out.println("## [ find all with sort ] ");
		displayPageElts(page);
		System.out.println("## --");		
	}
	
	private void saveAndFindById() {
		//check before
		//assertBefore();
		// saved and find
		EsBook saved = service.save(book1);
		equalsBook(saved,service.findById(book1.getId()));
		
		saved = service.save(book2);
		equalsBook(saved,service.findById(book2.getId()));
		
		saved = service.save(book3);
		equalsBook(saved,service.findById(book3.getId()));
		
		saved = service.save(book4);
		equalsBook(saved,service.findById(book4.getId()));
		
		saved = service.save(book4);
		equalsBook(saved,service.findById(book4.getId()));
		
		saved = service.save(book5);
		equalsBook(saved,service.findById(book5.getId()));
		
		saved = service.save(book6);
		equalsBook(saved,service.findById(book6.getId()));
	}
	
	
	private void equalsBook(EsBook book1, EsBook book2) {
		assertThat(book1, is(book2));
	}
	
	private void assertBefore() {
		assertThat(service.count(),is(0L));
	}
	
	private void createEntities() {
		// create author
		author1 = new EsAuthor();
		author1.setName("author1");
		author1.setAge(1);
		
		author2 = new EsAuthor();
		author2.setName("author2");
		author2.setAge(1);
		
		// create book
		book1 = new EsBook();
		book1.setTitle("book1");
		book1.setReleaseDate( randomDate() );
		book1.setAuthor(author1);
		
		book2 = new EsBook();
		book2.setTitle("book2");
		book2.setReleaseDate( randomDate() );
		book2.setAuthor(author2);
		
		book3 = new EsBook();
		book3.setTitle("book3");
		book3.setReleaseDate( randomDate() );
		book3.setAuthor(author1);
		
		book4 = new EsBook();
		book4.setTitle("book4");
		book4.setReleaseDate( randomDate() );
		book4.setAuthor(author1);
		
		book5 = new EsBook();
		book5.setTitle("book5");
		book5.setReleaseDate( randomDate() );
		book5.setAuthor(author1);
		
		book6 = new EsBook();
		book6.setTitle("book6");
		book6.setReleaseDate( randomDate() );
		book6.setAuthor(author2);		
	}
	
	private void displayPageElts(Page<EsBook> page) {
		System.out.println("## page size " + page.getTotalElements());
		if(page.hasContent()) {
			page.getContent().forEach((e)-> {
				System.out.println(e);
			});
		}
	}
	
	private Date randomDate() {
		Calendar cal = Calendar.getInstance();
		int year = (int)(Math.random()*15+2010);
		int month = (int)(Math.random()*12);		
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		int maxDate = cal.getActualMaximum(Calendar.DATE);
		int date = (int)(Math.random()*maxDate + 1);
		cal.set(Calendar.DATE, date);		
		//System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
		return cal.getTime();
	}
	

}









