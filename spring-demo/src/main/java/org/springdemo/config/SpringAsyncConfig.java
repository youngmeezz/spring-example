package org.springdemo.config;

import java.util.concurrent.Executor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Spring Async Config
 *
 * @author zaccoding
 * github : https://github.com/zacscoding
 */
@Configuration
@EnableAsync
@ComponentScan(basePackages = "org.springdemo.async")
public class SpringAsyncConfig {

    @Bean
    @Qualifier("asyncThreadPoolTaskExecutor")
    public Executor asyncThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        return pool;
    }
}
