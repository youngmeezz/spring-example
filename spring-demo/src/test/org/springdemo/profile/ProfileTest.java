package org.springdemo.profile;

import org.junit.Test;
import org.springdemo.domain.EnvProfile;
import org.springdemo.runner.AbstractTestRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author zacconding
 * @Date 2018-01-26
 * @GitHub : https://github.com/zacscoding
 */

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
