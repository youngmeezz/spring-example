package com.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author zaccoding
 * github : https://github.com/zacscoding
 * @Date : 2018-01-22
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
}
