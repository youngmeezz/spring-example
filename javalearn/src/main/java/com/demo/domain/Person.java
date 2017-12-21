package com.demo.domain;

import java.util.Date;

/**
 * @author zacconding
 * @Date 2017-12-21
 * @GitHub : https://github.com/zacscoding
 */
public class Person {
    private Integer seq;
    private String name;
    private Date regDate;

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    @Override
    public String toString() {
        return "Person{" +
                "seq=" + seq +
                ", name='" + name + '\'' +
                ", regDate=" + regDate +
                '}';
    }
}
