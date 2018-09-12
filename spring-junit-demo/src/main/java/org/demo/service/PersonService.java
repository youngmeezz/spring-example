package org.demo.service;

import org.demo.entity.Person;

/**
 * @author zacconding
 * @Date 2018-09-12
 * @GitHub : https://github.com/zacscoding
 */
public interface PersonService {

    int registPerson(Person person);

    int modifyPerson(Person person);

    Person getPersonById(Long id);

    int removePerson(Long id);
}
