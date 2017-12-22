package com.test.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.test.domain.Admin;

public class SecurityAdmin implements UserDetails {	
	private static final Logger logger = LoggerFactory.getLogger(SecurityAdmin.class);
	private static final long serialVersionUID = 1L;
	
	private Admin admin;
	private AdminRole role;
	
	public SecurityAdmin(Admin admin) {
		this.admin = admin;
		this.role = new AdminRole("ROLE_" + admin.getRole().toUpperCase());		
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(this.role);
		return authorities;
	}

	@Override
	public String getPassword() {
		return admin.getPassword();
	}
	
	@Override
	public String getUsername() {
		return admin.getId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {		
		return true;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public AdminRole getRole() {
		return role;
	}

	public void setRole(AdminRole role) {
		this.role = role;
	}
}
