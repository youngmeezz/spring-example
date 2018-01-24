package org.springdemo.runner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author zacconding
 * @Date 2018-01-25
 * @GitHub : https://github.com/zacscoding
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={
        "file:src/main/webapp/WEB-INF/config/root-context.xml",
        "file:src/main/webapp/WEB-INF/config/persistent-context.xml"
})
public abstract class AbstractTestRunner {}
