package com.test.json;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.test.domain.gson.SerializedDomain;

public class GsonSerilizedTest {    
    @Test
    public void useAnnotation() {
        // given
        Gson gson = new Gson();
        SerializedDomain domain = new SerializedDomain();
        domain.setDashesField("test name value");
        domain.setUnderScores("under");
        domain.setTransientField("transient");
        String expected = "{\"dashes-field\":\"test name value\",\"underScores\":\"under\"}";
        
        // when
        String toJson = gson.toJson(domain);
        
        // then
        assertThat(expected,is(toJson));
        
        // when
        SerializedDomain readDomain = gson.fromJson(toJson, SerializedDomain.class);
        
        // then
        assertThat(domain.getDashesField(),is(readDomain.getDashesField()));
        assertThat(domain.getUnderScores(),is(readDomain.getUnderScores()));
        assertNull(readDomain.getTransientField());
    }
    
    @Test
    public void useMultiple() {
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        
        SerializedDomain domain = new SerializedDomain();
        domain.setDashesField("test name value");
        domain.setUnderScores("under");
        domain.setTransientField("transient");
        
        String expected = "{\"dashes-field\":\"test name value\",\"under_scores\":\"under\",\"null_value\":null}";
        
        // when
        String toJson = gson.toJson(domain);
        
        // then
        assertThat(expected,is(toJson));
        
        // when
        SerializedDomain readDomain = gson.fromJson(toJson, SerializedDomain.class);
        
        // then
        assertThat(domain.getDashesField(),is(readDomain.getDashesField()));
        assertThat(domain.getUnderScores(),is(readDomain.getUnderScores()));
        assertNull(readDomain.getTransientField());                
        assertNull(readDomain.getNullValue());
    }
}
