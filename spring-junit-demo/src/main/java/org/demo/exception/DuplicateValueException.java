package org.demo.exception;

/**
 * @author zacconding
 * @Date 2018-09-13
 * @GitHub : https://github.com/zacscoding
 */
public class DuplicateValueException extends RuntimeException {

    public DuplicateValueException() {
        super();
    }

    public DuplicateValueException(String message) {
        super(message);
    }
}