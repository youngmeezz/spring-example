package com.test.security;

import javax.inject.Inject;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.test.domain.Admin;
import com.test.repository.AdminMemoryRepository;

public class AdminDetailServiceImpl implements UserDetailsService {	
//	@Inject
//	private AdminMapper adminMapper;
	
	@Inject
	private AdminMemoryRepository adminRepository;

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		Admin admin = null;
		admin = adminRepository.findOne(id);
		if (admin == null)
			return null;		
		return new SecurityAdmin(admin);
	}
}
