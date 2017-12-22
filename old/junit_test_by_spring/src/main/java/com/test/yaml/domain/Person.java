package com.test.yaml.domain;

import com.google.gson.Gson;

public class Person {
    private String firstName;
    private String lastName;
    private Integer age;
    
    public Person() {
        System.out.println("Person::constructor()");
    }
    
    public Person(String firstName,String lastName, Integer age) {
        System.out.println("Person::constructor(String firstName,String lastName, Integer age)");
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
    
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
