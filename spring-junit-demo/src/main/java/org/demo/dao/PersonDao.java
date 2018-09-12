package org.demo.dao;

import org.demo.entity.Person;

/**
 * @author zacconding
 * @Date 2018-09-12
 * @GitHub : https://github.com/zacscoding
 */
public interface PersonDao {

    int savePerson(Person person);

    int updatePerson(Person person);

    int deletePerson(Person person);

    Person findOneById(Long id);
}