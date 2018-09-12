package org.demo.entity;

/**
 * @author zacconding
 * @Date 2018-09-12
 * @GitHub : https://github.com/zacscoding
 */
public class Person {

    private long id;
    private int age;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", age=" + age + ", name='" + name + '\'' + '}';
    }
}
