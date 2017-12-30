package com.demo.security;


import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import sun.security.util.Password;

import java.util.Collection;

public class MemberAuthProvider implements AuthenticationProvider {
    private static final Logger logger = LoggerFactory.getLogger(MemberAuthProvider.class);
    @Autowired
    MemberDetailServiceImpl memberDetailService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String id = authentication.getName();
        String password = (String) authentication.getCredentials();

        UserDetails securityMember = memberDetailService.loadUserByUsername(id);

        // check id
        if (securityMember == null) {
            logger.debug("## securityMember is null");
            return null;
        }

        // check password
        if (!passwordEncoder.matches(password, securityMember.getPassword())) {
            logger.debug("## [login error] try to sign in {}, but not matched password", id);
            return null;
        }

        logger.debug("## success login " + securityMember);

        Collection<? extends GrantedAuthority> authorities = securityMember.getAuthorities();
        return new UsernamePasswordAuthenticationToken(securityMember, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
