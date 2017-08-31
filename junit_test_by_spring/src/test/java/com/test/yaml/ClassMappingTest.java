package com.test.yaml;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.test.domain.yaml.Config;
import com.test.domain.yaml.Invoice;
import com.test.domain.yaml.Person;

/**
 * Yamlk Class Mapping Test
 * 
 * https://bitbucket.org/asomov/snakeyaml/wiki/Documentation#markdown-header-tutorial 
 * 
 * @author zaccoding
 * @date 2017. 8. 12.
 */
public class ClassMappingTest {
    @Test    
    public void getEntityAssumeClass() {
        /**
         !!com.test.domain.yaml.Person
                 firstName: zaccoding
                 age: 99
         */
        // called Person() 
        String data = "!!com.test.domain.yaml.Person\nfirstName: Andrey\nage: 99";
        Object obj = construct(data);
        assertNotNull(obj);        
        assertTrue("Unexpected: " + obj.getClass().toString(), obj instanceof Person);
        Person person = (Person)obj;
        assertThat("Andrey",is(person.getFirstName()));
        assertNull(person.getLastName());
        assertEquals(99,person.getAge().intValue());
    }    
    
    @Test
    public void getConstructorBean() {
        // called Person(String,String,int)
        String data = "!!com.test.domain.yaml.Person [ zac, coding, 99 ]";
        Object obj = construct(data);
        Person person = (Person)obj;
        assertThat("zac",is(person.getFirstName()));
        assertThat("coding",is(person.getLastName()));
        assertEquals(99,person.getAge().intValue());
    }
    
    @Test
    public void complexClass() throws IOException {
        ClassPathResource resource = new ClassPathResource("yaml-test/invoice.yaml");
        Yaml yaml = new Yaml(new Constructor(Invoice.class));
        Invoice invoice = (Invoice)yaml.load(resource.getInputStream());        
        //System.out.println(invoice);
    }
    
    @Test
    public void innerConfig() throws IOException {
        ClassPathResource resource = new ClassPathResource("yaml-test/inner-config-test.yaml");
        Yaml yaml = new Yaml();
        Object obj = yaml.load(resource.getInputStream());        
        Config config = (Config)obj;
        assertThat(config.inner1.id,is("zaccoding"));
        assertThat(config.inner1.password,is("test"));
        
        assertThat(config.inner2.name,is("zac"));
        assertThat(config.inner2.age.intValue(),is(19));
    }
    
    private Object construct(String data) {
        return new Yaml().load(data);
    }
}
