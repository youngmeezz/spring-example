package org.springdemo.persistent.xml;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springdemo.domain.Person;
import org.springdemo.runner.AbstractTestRunner;
import org.springdemo.util.DataSouceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;

/**
 * @author zacconding
 * @Date 2018-01-25
 * @GitHub : https://github.com/zacscoding
 */
//@ActiveProfiles("ehcache_java_config")
@ActiveProfiles("ehcache_xml_config")
public class PersonDAOTest extends AbstractTestRunner {
    Person p1,p2,p3;
    @Autowired
    DataSource ds;
    @Autowired
    PersonDao personDao;
    @Before
    public void setUp() {
        p1 = new Person(null, "person1", 1);
        p2 = new Person(null, "person1", 2);
        p3 = new Person(null, "person3", 3);
    }

    @After
    public void tearDown() {
        DataSouceUtil.deleteAll(ds,"person");
        DataSouceUtil.dropAndCreateSeq(ds,"person_seq");
    }

    @Test
    public void test() {
        personDao.save(p1);
        personDao.save(p2);

        System.out.println("===================  First Call   ===============");
        personDao.findAllByName(p1.getName());
        System.out.println("====================================================");

        System.out.println("===================  Second Call   ===============");
        personDao.findAllByName(p1.getName());
        System.out.println("====================================================");
    }
}
