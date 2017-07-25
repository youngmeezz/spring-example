package com.test.domain.es;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.google.gson.Gson;

@Document( indexName = "data" , type = "access", shards = 1, replicas = 0)
public class Access {
	@Id
	private String id;	
	private String ip;
	@Field(type = FieldType.Nested)
	private List<Repository> repositories;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public List<Repository> getRepositories() {
		return repositories;
	}
	public void setRepositories(List<Repository> repositories) {
		this.repositories = repositories;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
	
}
