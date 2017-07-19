package com.test.security;
import org.springframework.security.core.GrantedAuthority;

public class AdminRole implements GrantedAuthority {	
	private static final long serialVersionUID = 1L;
	private String roleName;
	
	public AdminRole(String role) {
		this.roleName = role;
	}
	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String getAuthority() {
		return roleName;
	}

}
