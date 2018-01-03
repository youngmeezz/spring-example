package com.demo.security;

import com.demo.domain.Member;
import com.demo.security.domain.SecurityMember;
import com.demo.thread.ThreadLocalContext;
import com.demo.thread.ThreadLocalManager;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author zacconding
 * @Date 2018-01-03
 * @GitHub : https://github.com/zacscoding
 */
public abstract class SecurityUtil {

    /**
     * get current login user`s Member instance
     */
    public static Member getMember() {
        SecurityMember securityMember = getSecurityMember();
        return securityMember == null ? null : securityMember.getMember();
    }

    /**
     * get current login users`s SecurityMember instance
     * @return
     */
    public static SecurityMember getSecurityMember() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal == null || !(principal instanceof SecurityMember)) {
            return null;
        }

        return (SecurityMember) principal;
    }

    /**
     * get current login user id
     * @return
     */
    public static String getLoginId() {
        SecurityMember securityMember = getSecurityMember();

        if(securityMember == null) {
            return null;
        }

        Member member = securityMember.getMember();
        return member == null ? null : member.getLoginId();
    }

    public static void setMemberLogComment(String comment) {
        ThreadLocalContext context = ThreadLocalManager.getContextAndCreateIfNotExist();
        context.setMemberLogComment(comment);
    }






}
