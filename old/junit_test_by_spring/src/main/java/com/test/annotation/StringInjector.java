package com.test.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * StringInjector
 * Ref 
 *  : http://jdm.kr/blog/216
 * 
 * @author zaccoding
 * @date 2017. 8. 13.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface StringInjector {
    String value() default "This is StringInjector";
}
