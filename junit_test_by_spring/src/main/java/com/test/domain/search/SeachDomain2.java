package com.test.domain.search;

import com.google.gson.Gson;

public class SeachDomain2 {
	private Integer sequence;
	private String type;
	private String value;
	
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
