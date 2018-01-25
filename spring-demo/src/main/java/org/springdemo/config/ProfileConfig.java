package org.springdemo.config;

import org.springdemo.service.ProfileExampleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zacconding
 * @Date 2018-01-25
 * @GitHub : https://github.com/zacscoding
 */

@Configuration
public class Test1ProfileConfig {

    @Bean
    public ProfileExampleService profileExampleService() {
        // get current profile value == "profile_test1"


    }



}
