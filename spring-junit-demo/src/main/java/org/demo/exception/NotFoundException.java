package org.demo.exception;

/**
 * @author zacconding
 * @Date 2018-09-13
 * @GitHub : https://github.com/zacscoding
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }
}
