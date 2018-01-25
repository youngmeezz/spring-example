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