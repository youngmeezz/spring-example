package org.demo.config;

import org.springframework.context.annotation.Profile;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.util.EnumSet;

/**
 * Web Config (web.xml)
 *
 * @author zaccoding
 * github : https://github.com/zacscoding
 * @Date : 2018-01-17
 */
/*
@Profile("java-config")
public class WebInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // AppConfig context-param
        //AnnotationConfigWebApplicationContext ac = new AnnotationConfigWebApplicationContext();
        // ac.register(AppConfig.class);
        //servletContext.addListener(new ContextLoaderListener(ac));

        // WebAPPConfig
        AnnotationConfigWebApplicationContext wac = new AnnotationConfigWebApplicationContext();
        wac.register(WebAppConfig.class);


        // encoding filter
        FilterRegistration.Dynamic fr = servletContext.addFilter("encoding", org.springframework.web.filter.CharacterEncodingFilter.class);
        fr.setInitParameter("encoding", "utf-8");
        fr.addMappingForUrlPatterns(null, true, "/*");

        // hidden method filter
        FilterRegistration.Dynamic hiddenHttpMethod = servletContext.addFilter("hiddenHttpMethodFilter", org.springframework.web.filter.HiddenHttpMethodFilter.class);
        hiddenHttpMethod.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("appServlet", new DispatcherServlet(wac));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
}
*/