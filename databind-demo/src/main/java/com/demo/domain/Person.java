package com.demo.domain;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author zaccoding github : https://github.com/zacscoding
 */
@Getter
@Setter
@ToString
public class Person {

    private String name;
    private Integer age;
    private boolean work;
    private int[] intArray;
    private List<Integer> intList;
    private Address address;
    private List<Hobby> hobbies;
    private String job;

    public String getJsonValue() {
        return new Gson().toJson(this);
    }

    public String getJsonValue2() {
        return new Gson().toJson(this).replace("\\\\", "\\\\\\\\");
    }

    public String getJsonValue3() {
        return new Gson().toJson(this).replaceAll("\\\\", "\\\\\\\\");
    }
}
