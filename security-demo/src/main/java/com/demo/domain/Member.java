package com.demo.domain;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Member {
    private String loginId;
    private String name;
    private String password;
    private String ip;
    private LocalDateTime regDate;
    private List<MemberRole> roles;
}