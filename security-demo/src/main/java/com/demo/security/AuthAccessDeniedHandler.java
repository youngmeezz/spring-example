package com.demo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zacconding
 * @Date 2017-12-31
 * @GitHub : https://github.com/zacscoding
 */
public class AuthAccessDeniedHandler implements AccessDeniedHandler {
    private static final Logger logger = LoggerFactory.getLogger(AuthAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String context = request.getContextPath();
        int startIdx = 0;
        if (context != null && context.length() > 0) {
            startIdx = context.length();
        }
        String uri = request.getRequestURI().substring(startIdx);
        String redirectPath = context;

        if ("/login".equals(uri)) {
            redirectPath += "/";
        }
        else {
            redirectPath += "/403-errors";
        }

        logger.debug("## [access denied] uri : {}, redirectPath : {} ", uri, redirectPath);
        response.sendRedirect(redirectPath);
    }
}
