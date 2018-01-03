package com.demo.thread;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Current thread`s local variable
 *
 * @author zacconding
 * @Date 2018-01-03
 * @GitHub : https://github.com/zacscoding
 */
@Getter
@Setter
@ToString
public class ThreadLocalContext {
    private String memberLogComment;
}
