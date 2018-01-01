package com.demo.security.exception;

import org.springframework.security.authentication.LockedException;

/**
 * Exceed login attempts exception
 *
 * @author zacconding
 * @Date 2018-01-02
 * @GitHub : https://github.com/zacscoding
 */
public class ExceedLoginAttemptsException extends LockedException {
    public ExceedLoginAttemptsException() {
        super("");
    }
    public ExceedLoginAttemptsException(String msg) {
        super(msg);
    }
    public ExceedLoginAttemptsException(String msg, Throwable t) {
        super(msg, t);
    }
}
