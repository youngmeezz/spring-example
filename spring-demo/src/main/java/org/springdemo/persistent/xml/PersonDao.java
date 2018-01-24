package org.springdemo.persistent.xml;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdemo.domain.Person;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

/**
 * @author zacconding
 * @Date 2018-01-25
 * @GitHub : https://github.com/zacscoding
 */
@Repository
public class PersonDao {
    private static final Logger logger = LoggerFactory.getLogger(PersonDao.class);
    private static final String namespace= "org.springdemo.persistent.xml.PersonDao";
    @Inject
    private SqlSession session;

    @CacheEvict("personCache")
    public int save(Person person) {
        logger.info("## [request person save] person : {}", person);
        return session.insert(namespace + ".save", person);
    }

    @Cacheable(value="personCache")
    public List<Person> findAllByName(String name) {
        logger.info("## [request find all by name] name : {}", name);
        return session.selectList(namespace + ".findAllByName", name);
    }
}
