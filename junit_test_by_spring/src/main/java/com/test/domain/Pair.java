package com.test.domain;

import com.google.gson.Gson;

public class Pair<K,V> {
	private K key;
	private V value;
	
	public Pair(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	public K getKey() {
		return key;
	}
	
	public V getValue() {
		return value;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
