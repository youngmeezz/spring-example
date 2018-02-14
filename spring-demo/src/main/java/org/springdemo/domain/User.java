package org.springdemo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * result of  "https://api.github.com/users/zacscoding"
 *
 * @author zacconding
 * @Date 2018-02-14
 * @GitHub : https://github.com/zacscoding
 */
@Getter
@Setter
@ToString
public class User {

    private String login;
    private long id;
    private String htmlUrl;
    private String name;
    private String bio;
}
