package com.demo.test;

import static org.junit.Assert.*;

import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

public class RegexTest {
    // public static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.]+$");    
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._-]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,6}$");
    public static String EMAIL_PATTERN_STR = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,6}$";
    private String[] emails;
    private String[] notEmails;
    
    @Before
    public void setUp() {
        emails = new String[] {
                "test@test.com", "hiva@google.com", "test123aa@naver.com", "noteamil.dmail@google.com"
        };
        
        notEmails = new String[] {
                "notemail2@dhiv;.com", "test@@test.com", "test@testtest"
        };                
    }
    

    @Test
    public void matches() {
        for (String value : emails) {
            assertTrue(value, matches(EMAIL_PATTERN, value));
            assertTrue(value.matches(EMAIL_PATTERN_STR));
        }

        for (String value : notEmails) {
            assertFalse(value, matches(EMAIL_PATTERN, value));
            assertFalse(value.matches(EMAIL_PATTERN_STR));
        }        
    }
    
    public static boolean matches(Pattern pattern, CharSequence input) {
        if (pattern == null) {
            return false;
        }

        return pattern.matcher(input).matches();
    }

}
