## Spring examples!!

- <a href="#cache">Cache</a>


---

<div id="cache"> </div>

## Cache

1. maven dependency
2. config(xml, java)
3. apply
4. test

> Maven dependency

```
<!-- EhCacheCacheManager -->
<!-- https://mvnrepository.com/artifact/org.springframework/spring-context-support -->
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-context-support</artifactId>
  <version>${org.springframework-version}</version>
</dependency>
<!-- https://mvnrepository.com/artifact/net.sf.ehcache/ehcache -->
<dependency>
  <groupId>net.sf.ehcache</groupId>
  <artifactId>ehcache</artifactId>
  <version>2.10.3</version>
</dependency>
```

> config : java

```
@Configuration
@EnableCaching
@Profile("ehcache_java_config")
public class CacheConfig {
  /* ================
	 * 1) Simple Cache Manager
	 ================== */

    // Declare a cache manager
    //	@Bean
    //	public CacheManager cacheManager() {
    //		//  java.util.concurrent.ConcurrentHashMap 를 캐시 저장소로 사용
    //		return new ConcurrentMapCacheManager();
    //	}

	/* ================
	 * 2) EhCacheCacheManager
	 * docs :
	 * http://www.ehcache.org/documentation/3.3/
	 ================== */

    // Configure EhCacheCacheManager
    @Bean
    public EhCacheCacheManager cacheManager(CacheManager cm) {
        return new EhCacheCacheManager(cm);
    }

    // EhCacheManagerFactoryBean
    @Bean
    public EhCacheManagerFactoryBean ehcache() {
        EhCacheManagerFactoryBean ehCacheFactoryBean = new EhCacheManagerFactoryBean();

        ehCacheFactoryBean.setConfigLocation(new ClassPathResource("cache/ehcache.xml"));

        return ehCacheFactoryBean;
    }
}
```

> config : xml

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:cache="http://www.springframework.org/schema/cache"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.3.xsd
        http://www.springframework.org/schema/cache
        http://www.springframework.org/schema/cache/spring-cache.xsd">

    <beans profile="ehcache_xml_config">
        <cache:annotation-driven />
        <bean id="cacheManager" class="org.springframework.cache.concurrent.ConcurrentMapCacheManager" />
    </beans>

    <beans>
        <context:component-scan base-package="org.springdemo.config" />
    </beans>
</beans>
```

> apply

```
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

```

> Test

```
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

@ActiveProfiles("ehcache_java_config")
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

```

> Result 1) Not use ehcache

```
...
//@ActiveProfiles("ehcache_java_config")
//@ActiveProfiles("ehcache_xml_config")
public class PersonDAOTest extends AbstractTestRunner {
  ...
}

==>

===================  First Call   ===============
INFO : org.springdemo.persistent.xml.PersonDao - ## [request find all by name] name : person1
====================================================
===================  Second Call   ===============
INFO : org.springdemo.persistent.xml.PersonDao - ## [request find all by name] name : person1
====================================================
```

> Result 2) user ehcache

```
...
@ActiveProfiles("ehcache_java_config")
// @ActiveProfiles("ehcache_xml_config")
public class PersonDAOTest extends AbstractTestRunner {
  ...
}

==>

===================  First Call   ===============
INFO : org.springdemo.persistent.xml.PersonDao - ## [request find all by name] name : person1
====================================================
===================  Second Call   ===============
====================================================
```
