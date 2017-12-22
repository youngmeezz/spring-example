package com.test.domain.search;

import com.google.gson.Gson;

public class SearchDomain1 {	
	private Integer sequence;
	private String  id;
	private String password;
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
