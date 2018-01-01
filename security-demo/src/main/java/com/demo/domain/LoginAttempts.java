package com.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author zacconding
 * @Date 2017-12-31
 * @GitHub : https://github.com/zacscoding
 */
@Getter
@Setter
@ToString
public class LoginAttempts {
    private Long id;
    private String ip;
    private int attempts;
    private LocalDateTime lastModified;
}