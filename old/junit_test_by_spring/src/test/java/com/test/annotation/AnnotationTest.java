package com.test.annotation;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import com.test.annotation.container.MyContextContainer;
import com.test.domain.annotation.AnnotationDomain;

/**
 * Annotation Inject test
 * 
 * @author zaccoding
 * @date 2017. 8. 13.
 */
public class AnnotationTest {
    @Test
    public void stringInjector() {
        try {
            // when
            MyContextContainer container = new MyContextContainer();
            AnnotationDomain domain = container.get(AnnotationDomain.class);
            
            // then
            assertThat(domain.getName(),is("zaccoding"));
            assertThat(domain.getDefaultValue(),is("This is StringInjector"));
            assertNull(domain.getInvalidType());
            
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
