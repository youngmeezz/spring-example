package org.demo.service;

import org.demo.dao.PersonDao;
import org.demo.model.Person;
import org.demo.exception.DuplicateValueException;
import org.demo.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author zacconding
 * @Date 2018-09-12
 * @GitHub : https://github.com/zacscoding
 */
@Service
public class PersonServiceImpl implements PersonService {

    private PersonDao personDao;

    @Autowired
    public PersonServiceImpl(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public int regist(Person person) {
        if (personDao.getPersonById(person.getId()) != null) {
            throw new DuplicateValueException("Duplicate id");
        }

        return personDao.savePerson(person);
    }

    @Override
    public int modify(Person person) {
        Person saved = personDao.getPersonById(person.getId());
        if (saved == null) {
            return 0;
        }

        return personDao.updatePerson(person);
    }

    @Override
    public Person getPersonById(String id) {
        if (!StringUtils.hasLength(id)) {
            throw new RuntimeException();
        }

        Person person = personDao.getPersonById(id);
        if (person == null) {
            throw new NotFoundException();
        }

        return person;
    }

    @Override
    public int remove(String id) {
        return StringUtils.hasText(id) ? personDao.deletePerson(id) : 0;
    }
}