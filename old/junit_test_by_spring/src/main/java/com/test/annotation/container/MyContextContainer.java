package com.test.annotation.container;

import java.lang.reflect.Field;

import com.test.annotation.StringInjector;

/**
 * ref 
 * : http://jdm.kr/blog/216
 * 
 * @author zaccoding
 * @date 2017. 8. 13.
 */
public class MyContextContainer {
    public MyContextContainer(){}
    
    private <T> T invokeAnnotations(T instance) throws IllegalAccessException {
        Field[] fields = instance.getClass().getDeclaredFields();
        for(Field field : fields) {
            StringInjector annotation = field.getAnnotation(StringInjector.class);
            if(annotation != null && field.getType() == String.class) {
                field.setAccessible(true);
                field.set(instance, annotation.value());
            }
        }
        
        return instance;
    }
    
    @SuppressWarnings("unchecked")
    public <T> T get(Class<?> clazz) throws IllegalAccessException, InstantiationException {
        T instance = (T)clazz.newInstance();
        instance = invokeAnnotations(instance);
        return instance;        
    }

}
