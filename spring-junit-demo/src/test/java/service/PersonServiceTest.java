package service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.demo.dao.PersonDao;
import org.demo.exception.DuplicateValueException;
import org.demo.exception.NotFoundException;
import org.demo.model.Person;
import org.demo.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author zacconding
 * @Date 2018-09-13
 * @GitHub : https://github.com/zacscoding
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring/root-context.xml", "classpath:spring/persistence-context.xml"})
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonDao personDao;

    @Test(expected = DuplicateValueException.class)
    public void saveDuplicatedId() {
        // given
        Person p1 = new Person("1", "hiva", 10);
        assertTrue(personDao.savePerson(p1) > 0);

        // when
        Person p2 = new Person(p1.getId(), "new-hiva", 12);
        personService.regist(p2);

        fail();
    }

    @Test(expected = NotFoundException.class)
    public void getPersonInvalidParam() {
        // given
        Person p1 = new Person("1", "hiva", 10);
        assertTrue(personDao.savePerson(p1) > 0);

        try {
            personService.getPersonById(null);
            fail();
        } catch(Exception e) {
        }

        personService.getPersonById(p1.getId() + "-invalid");

        fail();
    }
}