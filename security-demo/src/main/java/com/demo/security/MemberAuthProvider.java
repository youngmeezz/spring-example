package com.demo.security;


import com.demo.domain.LoginAttempts;
import com.demo.mapper.LoginAttemptsMapper;
import com.demo.security.exception.ExceedLoginAttemptsException;
import com.demo.security.exception.LoginNotMatchedException;
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
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import sun.security.util.Password;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class MemberAuthProvider implements AuthenticationProvider {
    private static final Logger logger = LoggerFactory.getLogger(MemberAuthProvider.class);
    @Autowired
    private MemberDetailServiceImpl memberDetailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private LoginAttemptsMapper loginAttemptsMapper;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String id = authentication.getName();
        String password = (String) authentication.getCredentials();

        // extract user ip & check try cnt
        WebAuthenticationDetails wad = (WebAuthenticationDetails) authentication.getDetails();
        String ip = wad.getRemoteAddress();
        checkLoginAttempts(ip);

        UserDetails securityMember = memberDetailService.loadUserByUsername(id);

        // check id & password
        if (securityMember == null || !passwordEncoder.matches(password, securityMember.getPassword())) {
            logger.debug("## [failed to sign in] id : {}", id);
            throw new LoginNotMatchedException();
        }

        logger.debug("## success login " + securityMember);

        Collection<? extends GrantedAuthority> authorities = securityMember.getAuthorities();
        return new UsernamePasswordAuthenticationToken(securityMember, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

    private void checkLoginAttempts(String ip) {
        LoginAttempts loginAttempts = loginAttemptsMapper.findByIp(ip);
        final int lockedMinutes = 5;
        final int limitOfAttempts = 3;

        // exist failure
        if (loginAttempts != null) {
            LocalDateTime lastModified = loginAttempts.getLastModified();
            if (lastModified != null) {
                long diff = System.currentTimeMillis() - lastModified.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                // check locked time
                if(TimeUnit.MILLISECONDS.toMinutes(diff) >= lockedMinutes) {
                    loginAttemptsMapper.resetFailAttempts(ip);
                }
                // exceed login attempts
                else if(loginAttempts.getAttempts() >= limitOfAttempts) {
                    throw new ExceedLoginAttemptsException();
                }
            }
        }
    }
}
