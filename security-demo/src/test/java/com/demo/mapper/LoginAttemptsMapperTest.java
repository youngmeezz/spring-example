package com.demo.mapper;

import com.demo.domain.LoginAttempts;
import com.demo.runner.AbstractTestRunner;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * @author zacconding
 * @Date 2018-01-02
 * @GitHub : https://github.com/zacscoding
 */
public class LoginAttemptsMapperTest extends AbstractTestRunner {
    @Autowired
    LoginAttemptsMapper loginAttemptsMapper;

    @Test
    public void test() {
        loginAttemptsMapper.deleteAll();

        // given
        String ip = "127.0.0.1";
        // when
        int saveResult = loginAttemptsMapper.save(ip);
        // then
        assertTrue(saveResult == 1);

        // when
        LoginAttempts find = loginAttemptsMapper.findByIp(ip);
        // then
        assertThat(find.getIp(),is(ip));
        assertTrue(find.getAttempts() == 1);

        // when
        loginAttemptsMapper.updateFailAttempts(ip);
        // then
        LoginAttempts find2 = loginAttemptsMapper.findByIp(ip);
        assertTrue(find2.getAttempts() == 2);

        // when
        loginAttemptsMapper.resetFailAttempts(ip);
        // then
        LoginAttempts find3 = loginAttemptsMapper.findByIp(ip);
        assertTrue(find3.getAttempts() == 0);
        assertNull(find3.getLastModified());
    }
}
