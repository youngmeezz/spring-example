package com.test.reflectiontest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.Test;

import com.test.domain.relection.ISample;
import com.test.domain.relection.Sample;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

public class ProxyTest {
    @Test
    public void dynamicProxyByReflection() {       
        System.out.println("dynamic proxy by reflection");
        ISample sample = newInstanceByReflection();
        sample.setName("Zaccoding");
    }
    
    @Test
    public void dynamicProxyByJavaAssist() throws Exception {
        System.out.println("dynamic proxy by javassist");
        ISample sample = newInstanceByJavaAssist();
        sample.setName("Zaccoding");
    }
    
    private ISample newInstanceByJavaAssist() throws InstantiationException, IllegalAccessException {
        ProxyFactory factory = new ProxyFactory();
        factory.setSuperclass(Sample.class);
        Class<?> aClass = factory.createClass();
        final ISample newInstance = (ISample)aClass.newInstance();
        MethodHandler methodHandler = new MethodHandler() {
            @Override
            public Object invoke(Object self, Method overridden, Method proceed, Object[] args) throws Throwable {
                System.out.println("## [before] Sample :: " + overridden.getName());
                System.out.println("## [before] Sample :: " + proceed.getName());
                return proceed.invoke(newInstance, args);
            }            
        };
        
        ((ProxyObject)newInstance).setHandler(methodHandler);
        
        return newInstance;        
    }
    
    private ISample newInstanceByReflection() {
        final Sample sample = new Sample();
        
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("## [before] Sample :: " + method.getName());
                return method.invoke(sample, args);
            }
        };
        
        return (ISample)Proxy.newProxyInstance(ProxyTest.class.getClassLoader(), new Class[]{ISample.class}, invocationHandler);        
    }
    

}
