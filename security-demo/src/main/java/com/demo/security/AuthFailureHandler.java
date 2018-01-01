package com.demo.security;

import com.demo.domain.LoginAttempts;
import com.demo.mapper.LoginAttemptsMapper;
import com.demo.security.exception.ExceedLoginAttemptsException;
import com.demo.security.exception.LoginNotMatchedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zacconding
 * @Date 2018-01-02
 * @GitHub : https://github.com/zacscoding
 */
public class AuthFailureHandler implements AuthenticationFailureHandler {
    private static final Logger logger = LoggerFactory.getLogger(AuthFailureHandler.class);
    @Autowired
    private LoginAttemptsMapper loginAttemptsMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // extract login id
        String loginId = request.getParameter("loginid");
        boolean isExceedAttempts = false;
        request.setAttribute("loginid", loginId);

        String message = null;
        // Not exist id or Not matched password
        if(exception instanceof LoginNotMatchedException || exception instanceof InternalAuthenticationServiceException) {
            message = "Please confirm ur id and password";

        }
        // exceed login attempts
        else if(exception instanceof ExceedLoginAttemptsException) {
            isExceedAttempts = true;
            message = "Exceed login attempts. Please try again 5 minutes later";
        }
        else {
            if(exception != null) {
                logger.debug("## [not handle exception in auth failure handler] exception : {} , message : {}", new Object[] {
                        exception.getClass().getName(), exception.getMessage()
                });
            }
        }

        request.setAttribute("failMessage", message);

        // update login failed attempts
        if (!isExceedAttempts) {
            String ip = request.getRemoteHost();
            LoginAttempts attempts = loginAttemptsMapper.findByIp(ip);

            if(attempts == null) {
                loginAttemptsMapper.save(ip);
            }
            else {
                loginAttemptsMapper.updateFailAttempts(ip);
            }
        }
        // response.sendRedirect("/login");
        logger.debug("## request.getRequestURL() : {} ", request.getRequestURL());
        logger.debug("## request.getRequestURI() : {} " , request.getRequestURI());
        request.getRequestDispatcher("/loginPage").forward(request,response);
    }
}
