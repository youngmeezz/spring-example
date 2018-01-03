package com.demo.security;

import com.demo.domain.Member;
import com.demo.security.domain.SecurityMember;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 * @author zacconding
 * @Date 2017-12-31
 * @GitHub : https://github.com/zacscoding
 */
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(AuthSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object securityMember = authentication.getPrincipal();

        // set remote host
        if(securityMember instanceof SecurityMember) {
            logger.debug("## [success to auth] Security Member : {}", securityMember);
            Member member  = ((SecurityMember) securityMember).getMember();
            member.setIp(request.getRemoteHost());
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
