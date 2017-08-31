package com.test.yaml;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.yaml.snakeyaml.Yaml;

/**
 * Tutorial in documents
 * 
 * https://bitbucket.org/asomov/snakeyaml/wiki/Documentation#markdown-header-tutorial
 * 
 * @author zaccoding
 * @date 2017. 8. 12.
 */
public class LoadTest {
    @Test
    @Ignore
    @SuppressWarnings("unchecked")
    public void loadFromString() {
        Yaml yaml = new Yaml();
        final String[] values = new String[]{"Value1", "Value2","Value3"};
        final String prefix="\n- ";
        String document = "";
        // document == "\n- Value1\n- Value2\n- Value3"
        for(String value : values) {
            document += (prefix + value);
        }
        
        List<String> readList = (List<String>)yaml.load(document);
        
        assertTrue(values.length==readList.size());
        for(int i=0; i<values.length; i++) {
            assertThat(values[i],is(readList.get(i)));
        }        
    }
    
    @Test
    @Ignore
    public void loadFromStream() throws IOException {
        /* workspace/target/test-classes/utf-8.txt == src/test/resources/utf-8 */
        ClassPathResource resource = new ClassPathResource("yaml-test/utf-8.txt");
        InputStream is = resource.getInputStream();
        Yaml yaml = new Yaml();
        Object data = yaml.load(is);
        assertEquals(data,"test");
        data = yaml.load(new ByteArrayInputStream("test2".getBytes()));
        assertEquals(data,"test2");
    }
    
    @Test
    @Ignore
    public void loadFromStreamWithDocs() throws IOException {        
        ClassPathResource resource = new ClassPathResource("yaml-test/example2_28.yaml");
        Yaml yaml = new Yaml();
        int counter = 0;
        for(Object data : yaml.loadAll(resource.getInputStream())) {
            System.out.println("##\t start \t ##");
            System.out.println(data);
            System.out.println("##\t end \t ##");
            counter++;            
        }        
        assertTrue(counter == 3);                
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void test() throws IOException {
        ClassPathResource resource = new ClassPathResource("yaml-test/any-object-example.yaml");
        Yaml yaml = new Yaml();
        Map<String,Object> map = (Map<String,Object>)yaml.load(resource.getInputStream());
        map.forEach((k,v) -> {
            System.out.println("key : " + k + ", value : " + v);
        });
    }    
}
