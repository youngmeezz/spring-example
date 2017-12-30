package com.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author zacconding
 * @Date 2017-12-31
 * @GitHub : https://github.com/zacscoding
 */
@Getter
@Setter
@ToString
public class MemberRole implements GrantedAuthority {
    private static final String ROLE_PREFIX = "ROLE_";
    private Long id;
    private String roleName;
    private String loginId;

    @Override
    public String getAuthority() {
        return ROLE_PREFIX + roleName.toUpperCase();
    }
}