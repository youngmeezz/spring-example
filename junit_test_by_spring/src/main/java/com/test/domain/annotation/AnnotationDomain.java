package com.test.domain.annotation;

import com.test.annotation.StringInjector;

public class AnnotationDomain {
    @StringInjector("zaccoding")
    private String name;
    @StringInjector
    private String defaultValue;
    @StringInjector
    private Integer invalidType;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDefaultValue() {
        return defaultValue;
    }
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
    public Integer getInvalidType() {
        return invalidType;
    }
    public void setInvalidType(Integer invalidType) {
        this.invalidType = invalidType;
    }
}
