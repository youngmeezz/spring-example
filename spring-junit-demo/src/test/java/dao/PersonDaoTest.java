package dao;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.demo.dao.PersonDao;
import org.demo.model.Person;
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
public class PersonDaoTest {

    @Autowired
    private PersonDao personDao;

    @Test
    public void saveTest() {
        // given
        Person person = new Person();
        person.setId("123");
        person.setName("hivavava");
        person.setAge(10);

        // when
        int result = personDao.savePerson(person);

        // then
        assertTrue(result == 1);
    }

    @Test
    public void getPersonByIdTest() {
        // given
        Person p1 = new Person("123", "hivava", 10);
        assertTrue(personDao.savePerson(p1) > 0);
        Person p2 = new Person("125", "hiva", 15);
        assertTrue(personDao.savePerson(p2) > 0);

        // when
        Person find = personDao.getPersonById("123");

        // then
        assertThat(find.getId(), is(p1.getId()));
        assertThat(find.getName(), is(p1.getName()));
        assertTrue(find.getAge() == p1.getAge());
    }

    @Test
    public void updatePersonTest() {
        // given
        Person p1 = new Person("123", "hivava", 10);
        assertTrue(personDao.savePerson(p1) > 0);
        Person p2 = new Person("125", "hiva", 15);
        assertTrue(personDao.savePerson(p2) > 0);

        p1.setAge(20);
        p1.setName("new-hivava");

        // when
        int result = personDao.updatePerson(p1);

        // then
        assertTrue(result > 0);
        Person find = personDao.getPersonById(p1.getId());
        assertThat(find.getName(), is(p1.getName()));
        assertTrue(find.getAge() == p1.getAge());
    }

    @Test
    public void deleteTest() {
        // given
        Person p1 = new Person("123", "hivava", 10);
        assertTrue(personDao.savePerson(p1) > 0);

        // when
        int result = personDao.deletePerson(p1.getId());

        // then
        assertTrue(result > 0);
        Person find = personDao.getPersonById(p1.getId());
        assertNull(find);
    }
}