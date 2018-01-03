package com.demo.domain;

import com.demo.domain.enums.MemberLogType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author zacconding
 * @Date 2018-01-03
 * @GitHub : https://github.com/zacscoding
 */
@Getter
@Setter
@ToString
public class MemberLog {
    private Long seq;
    private String loginId;
    private String ip;
    private MemberLogType type;
    private String comment;
    private LocalDateTime actionTime;
}
