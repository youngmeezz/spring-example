package com.demo.security.exception;

import org.springframework.security.authentication.LockedException;

/**
 * Login failure exception
 * not exist id or not matched password
 *
 * @author zacconding
 * @Date 2018-01-02
 * @GitHub : https://github.com/zacscoding
 */
public class LoginNotMatchedException extends LockedException {
    public LoginNotMatchedException() {
        super("");
    }
    public LoginNotMatchedException(String msg) {
        super(msg);
    }
    public LoginNotMatchedException(String msg, Throwable t) {
        super(msg, t);
    }
}
