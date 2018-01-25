## Spring examples!!

- <a href="#cache">Cache</a>
- <a href="#profile">Profile</a>


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

---

<div id="profile"></div>

## Profile  
- org.springdemo.config.ProfileConfig  
- org.springdemo.domain.EnvProfile
- web.xml
- WEB-INF/config/root-context.xml  
- test/org.springdemo.profile.ProfileTest 

> Generate Beans depend on profile  

1. Java Config  

```
package org.springdemo.config;

import org.springdemo.domain.EnvProfile;
import org.springdemo.util.CustomPrinter;
import org.springdemo.util.GsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

@Configuration
@Profile("profile_java_config")
public class ProfileConfig {
    @Autowired
    private Environment env;

    @Bean
    @Profile("profile_test1")
    public EnvProfile envProfile() {
        CustomPrinter.println("## [ProfileConfig.envProfile() is called] activated profiles : {}", GsonUtil.toString(getActivatedProfiles()));
        EnvProfile profile = new EnvProfile();
        profile.setEnvProfile("profile_test1");
        return profile;
    }

    @Bean
    @Profile("profile_test2")
    public EnvProfile envProfile2() {
        CustomPrinter.println("## [ProfileConfig.envProfile2() is called] activated profiles : {}", GsonUtil.toString(getActivatedProfiles()));
        EnvProfile profile = new EnvProfile();
        profile.setEnvProfile("profile_test2");
        return profile;
    }

    private String[] getActivatedProfiles() {
        // get current activated profiles
        String[] activeProfiles = env.getActiveProfiles();
        return (activeProfiles == null) ? new String[]{} : activeProfiles;
    }
}
```


2. XML  

```
<beans profile="profile_xml_config">
  <beans profile="profile_test1">
    <bean id="envProfile" class="org.springdemo.domain.EnvProfile">
      <property name="envProfile" value="profile_test1" />
    </bean>
  </beans>
  <beans profile="profile_test2">
    <bean id="envProfile2" class="org.springdemo.domain.EnvProfile">
      <property name="envProfile" value="profile_test2" />
    </bean>
  </beans>
</beans>
```

3. Activate Profile web.xml  

```
<!-- Set default profile for context -->
<context-param>
  <param-name>spring.profiles.active</param-name>  
  <param-value>profile_xml_config,profile_test1</param-value>
</context-param>
```

4. Acivate Profile in test  

```
package org.springdemo.profile;

import org.junit.Test;
import org.springdemo.domain.EnvProfile;
import org.springdemo.runner.AbstractTestRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"profile_java_config","profile_test1"})
// @ActiveProfiles({"profile_java_config","profile_test2"})
// @ActiveProfiles({"profile_xml_config","profile_test1"})
//@ActiveProfiles({"profile_xml_config","profile_test2"})
public class ProfileTest extends AbstractTestRunner {
    @Autowired
    EnvProfile envProfile;

    @Test
    public void profile() {
        System.out.println("## " + envProfile);
    }
}
```

> Test Result  

```
## [ProfileConfig.envProfile() is called] activated profiles : ["profile_java_config","profile_test1"]
## EnvProfile(envProfile=profile_test1)
```

5. Get activated profile

```
package org.springdemo.config;

import org.springdemo.domain.EnvProfile;
import org.springdemo.util.CustomPrinter;
import org.springdemo.util.GsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

/**
 * @author zacconding
 * @Date 2018-01-25
 * @GitHub : https://github.com/zacscoding
 */

@Configuration
@Profile("profile_java_config")
public class ProfileConfig {
    @Autowired
    private Environment env;    
    ...
    private String[] getActivatedProfiles() {
        // get current activated profiles
        String[] activeProfiles = env.getActiveProfiles();
        return (activeProfiles == null) ? new String[]{} : activeProfiles;
    }
}
```

---
