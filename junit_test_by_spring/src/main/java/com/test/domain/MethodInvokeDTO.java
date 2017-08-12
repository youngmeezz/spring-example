package com.test.domain;

public class MethodInvokeDTO {
    private Class<?> methodClass;
    private String methodName;
    private Object methodInvoker;
    private Class<?>[] parameterClasses;
    private Class<?> returnTypeClass;
    private Object[] parameterValues;
    
    public Class<?> getMethodClass() {
        return methodClass;
    }
    public void setMethodClass(Class<?> methodClass) {
        this.methodClass = methodClass;
    }
    public String getMethodName() {
        return methodName;
    }
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    public Object getMethodInvoker() {
        return methodInvoker;
    }
    public void setMethodInvoker(Object methodInvoker) {
        this.methodInvoker = methodInvoker;
    }
    public Class<?>[] getParameterClasses() {
        return parameterClasses;
    }
    public void setParameterClasses(Class<?>[] parameterClasses) {
        this.parameterClasses = parameterClasses;
    }
    public Class<?> getReturnTypeClass() {
        return returnTypeClass;
    }
    public void setReturnTypeClass(Class<?> returnTypeClass) {
        this.returnTypeClass = returnTypeClass;
    }
    public Object[] getParameterValues() {
        return parameterValues;
    }
    public void setParameterValues(Object[] parameterValues) {
        this.parameterValues = parameterValues;
    }
}
