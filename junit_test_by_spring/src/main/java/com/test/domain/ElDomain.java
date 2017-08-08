package com.test.domain;

import com.google.gson.Gson;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ElDomain {
	private boolean isJoined;
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
