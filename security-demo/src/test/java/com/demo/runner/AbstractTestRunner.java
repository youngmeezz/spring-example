package com.demo.runner;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
        "file:src/main/webapp/WEB-INF/config/root-context.xml",
        "file:src/main/webapp/WEB-INF/config/persistent-context.xml",
        "file:src/main/webapp/WEB-INF/config/security-context.xml"
        })
public abstract class AbstractTestRunner {
    // empty
}
