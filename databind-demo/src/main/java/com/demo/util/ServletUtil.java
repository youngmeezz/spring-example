package com.demo.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zaccoding
 * github : https://github.com/zacscoding
 * @Date : 2018-01-18
 */
public abstract class ServletUtil {
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static String getRequestURI() {
        return getHttpServletRequest().getRequestURI();
    }
}
