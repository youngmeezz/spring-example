package org.demo.config;

import java.util.concurrent.TimeUnit;
import org.demo.oauth.bo.NaverLoginBO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * @author zacconding
 * @Date 2018-02-07
 * @GitHub : https://github.com/zacscoding
 */
@ComponentScan(basePackages = {"org.demo.controller"})
@EnableWebMvc
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    private static final String VIEW_RESOLVER_PREFIX = "/WEB-INF/views/";
    private static final String VIEW_RESOLVER_SUFFIX = ".jsp";

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix(VIEW_RESOLVER_PREFIX);
        viewResolver.setSuffix(VIEW_RESOLVER_SUFFIX);
        return viewResolver;
    }

    @Bean
    public NaverLoginBO naverLoginBO() {
        return new NaverLoginBO();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        CacheControl control = CacheControl.maxAge(1, TimeUnit.DAYS).cachePublic();
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/").setCacheControl(control);
    }
}