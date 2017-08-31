package com.test.util;

import static org.junit.Assert.assertNotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.test.domain.MethodInvokeDTO;

public class MethodInvokeUtils {    
    @SuppressWarnings("unchecked")
    public static <T> T invoke(MethodInvokeDTO dto,T returnType) {
        Class<?> clazz = dto.getMethodClass();
        assertNotNull("Class must be not null in MethodInvokeDTO",clazz);        
        try {
            Method method = clazz.getDeclaredMethod(dto.getMethodName(), dto.getParameterClasses());
            Object returnValue = method.invoke(dto.getMethodInvoker(),dto.getParameterValues());
            if(method.getReturnType() != returnType) {
                System.out.println("type mismatch");
                return null;
            }
            return (T)(returnValue);            
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        } catch(IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
}
