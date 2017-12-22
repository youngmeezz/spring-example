package com.test.repository.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.test.domain.EsBook;

public interface EsBookRepository extends ElasticsearchRepository<EsBook, String> {
	
}
