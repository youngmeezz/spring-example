package org.demo.service;

import org.demo.model.Person;

/**
 * @author zacconding
 * @Date 2018-09-12
 * @GitHub : https://github.com/zacscoding
 */
public interface PersonService {

    int regist(Person person);

    int modify(Person person);

    Person getPersonById(String id);

    int remove(String id);
}
