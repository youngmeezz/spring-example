package com.test.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.test.domain.Admin;

@Component
public class AdminMemoryRepository {
	private Map<String,Admin> adminMap;
	public void init() {
		adminMap = new HashMap<>();
		
		Admin admin = new Admin();
		admin.setId("admin");
		admin.setPassword("admin");
		admin.setRole("ADMIN");
		adminMap.put(admin.getId(),admin);
		
		for(int i=1;i<10;i++) {
			Admin admin2 = new Admin();
			admin2.setId("admin"+i);
			admin2.setPassword("admin");
			admin2.setRole("ADMIN");
			adminMap.put(admin2.getId(),admin2);
		}		
	}
	
	public Admin findOne(String id) {
		return adminMap.get(id);
	}
}
