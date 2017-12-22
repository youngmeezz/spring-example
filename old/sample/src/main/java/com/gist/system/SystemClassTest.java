package com.gist.system;

import org.junit.Test;

import com.gist.util.CustomLogger;

public class SystemClassTest {
    public String sample1 = "sample1";
    public String sample2 = "sample2";

    @Test
    public void identifyHashcode() {
        CustomLogger.println("sample1 : {} , sample2 : {}",
                new Object[] {System.identityHashCode(sample1), System.identityHashCode(sample2)});

    }

}
