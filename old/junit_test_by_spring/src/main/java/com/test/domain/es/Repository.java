package com.test.domain.es;

import java.util.List;

import com.google.gson.Gson;

public class Repository {	
	private String ip;	
	private List<String> prepared;
	private List<String[]> values;	
	private List<String> labels;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public List<String> getPrepared() {
		return prepared;
	}
	public void setPrepared(List<String> prepared) {
		this.prepared = prepared;
	}
	public List<String[]> getValues() {
		return values;
	}
	public void setValues(List<String[]> values) {
		this.values = values;
	}
	public List<String> getLabels() {
		return labels;
	}
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
