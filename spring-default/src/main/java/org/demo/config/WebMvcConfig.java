//package org.demo.config;
//
//import java.util.List;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.web.servlet.ViewResolver;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//import org.springframework.web.servlet.view.InternalResourceViewResolver;
//
///**
// * @author zacconding
// * @Date 2018-02-26
// * @GitHub : https://github.com/zacscoding
// */
//@Profile("java_config")
//@Configuration
//@ComponentScan(basePackages = {"org.demo.web"})
//public class WebMvcConfig extends WebMvcConfigurationSupport {
//
//
//    @Override
//    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(new MappingJackson2HttpMessageConverter());
//    }
//
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("chat");
//    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/resources/**").addResourceLocations("resources/");
//    }
//
//    @Bean
//    public ViewResolver viewResolver() {
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        resolver.setPrefix("/WEB-INF/views/");
//        resolver.setSuffix(".jsp");
//        return resolver;
//    }
//}
