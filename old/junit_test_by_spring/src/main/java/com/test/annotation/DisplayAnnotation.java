package com.test.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Reference 
 *  : http://jdm.kr/blog/216
 * @author zaccoding
 * @date 2017. 8. 13.
 */

@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME) // 컴파일 이후 JVM에 의해 참조가 가능
//@Retention(RetentionPolicy.CLASS) // 컴파일러가 클래스 참조할 때까지 유효 == default
//@Retention(RetentionPolicy.SOURCE) // 컴파일 이후 사라짐
@Target({
    ElementType.PACKAGE, // 패키지 선언시
    ElementType.TYPE, // 타입 선언시
    ElementType.CONSTRUCTOR, // 생성자 선언시
    ElementType.FIELD, // 멤버 변수 선언시
    ElementType.METHOD, // 메소드 선언시
    ElementType.ANNOTATION_TYPE, // 어노테이션 타입 선언시
    ElementType.LOCAL_VARIABLE, // 지역 변수 선언시
    ElementType.PARAMETER, // 매개 변수 선언시
    ElementType.TYPE_PARAMETER, // 매개 변수 타입 선언시
    ElementType.TYPE_USE // 타입 사용시
})
public @interface DisplayAnnotation {
    public enum Quality {BAD,GOOD,VERYGOOD}
    String value();
    int[] values();
    Quality quality() default Quality.GOOD;
}
