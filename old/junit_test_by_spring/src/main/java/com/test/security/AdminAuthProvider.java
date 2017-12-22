package com.test.security;

import java.util.Collection;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AdminAuthProvider implements AuthenticationProvider {	
	private static final Logger logger= LoggerFactory.getLogger(AdminAuthProvider.class);	
	@Inject 
	private AdminDetailServiceImpl service;
	@Inject 
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String id = authentication.getName();
		String password = (String) authentication.getCredentials();
		
		logger.debug("id" + id);
		logger.debug("pw : " + password);
		
		UserDetails securityAdmin = service.loadUserByUsername(id);
		if(securityAdmin == null || !securityAdmin.getUsername().equalsIgnoreCase(id)) 
			throw new BadCredentialsException("Username not found");
		
		if(!password.equals(securityAdmin.getPassword()))
			throw new BadCredentialsException("Wrong password");
		
		if(!securityAdmin.isEnabled())
			throw new DisabledException("Has no auth");
		
		Collection<? extends GrantedAuthority> authorities = securityAdmin.getAuthorities();
		return new UsernamePasswordAuthenticationToken(securityAdmin,password,authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}
}
