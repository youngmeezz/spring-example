package com.test.reflectiontest;

import static org.junit.Assert.assertTrue;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.junit.Test;

public class StringInvokerTest {
    
    @Test
    public void stringMethods() {
        Method[] methods = String.class.getDeclaredMethods();
        for(Method method : methods) {
            displayMethodInform(method);
        }
    }
    
    private void displayMethodInform(Method method) {
        System.out.println("-----------------------------------------------------------");
        System.out.println("getName() : " + method.getName());
        Parameter[] params = method.getParameters();
        if(params != null && params.length > 0) {
            System.out.println("-- display getParameters() --");
            for(Parameter param : params) {
                System.out.println("\t param : " + param.getName() + ", "+param.getType().getName());
            }
        }
        System.out.println("-----------------------------------------------------------");
    }
    
    //@Test
    public void test() throws Exception {
        assertTrue(invoke1("test","test","contains"));
    }
    
    private boolean invoke2(String invoker,String param, String methodName) throws Exception {
        Method method = new PropertyDescriptor(methodName,invoker.getClass()).getWriteMethod();
        return (Boolean)method.invoke(invoker, param);
    }
    
    private boolean invoke1(String invoker,String param, String methodName) throws Exception {
        //Method method = invoker.getClass().getDeclaredMethod(methodName, new Class[]{});
        Method method = invoker.getClass().getMethod(methodName, new Class[]{});
        if(method == null || !method.isAccessible()) {
            System.out.println("method is null || not accessible");
            return false;
        }
        
        if(method.getReturnType() != Boolean.class && method.getReturnType() != boolean.class ) {
            System.out.println("this method must return boolean type current type : " + method.getReturnType());
            return false;
        }
        
        return (Boolean)method.invoke(invoker, param);
    }
    
    
    
    //@Test
    public void test2() {
        Method[] methods = String.class.getMethods();
        displayMethods(methods);
        methods = String.class.getDeclaredMethods();
        displayMethods(methods);
    }
    
    private void displayMethods(Method[] methods) {
        System.out.println("####################################################");
        for(Method method : methods) {            
            System.out.println(method.getName());
        }
        System.out.println("####################################################");
    }

}
