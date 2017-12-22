package com.test.reflectiontest;

import org.junit.Test;

import com.test.domain.MethodInvokeDTO;
import com.test.util.MethodInvokeUtils;

public class InvokeUtilsTest {    
    @Test
    public void test() {
        MethodInvokeDTO dto = new MethodInvokeDTO();
        dto.setMethodInvoker("test");
        dto.setMethodName("equals");
        dto.setMethodClass(String.class);
        dto.setParameterClasses(new Class<?>[]{Object.class});
        dto.setParameterValues(new Object[]{"test"});        
        System.out.println(MethodInvokeUtils.invoke(dto, boolean.class));        
    }
}
