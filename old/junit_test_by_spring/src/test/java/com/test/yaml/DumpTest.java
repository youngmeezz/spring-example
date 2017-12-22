package com.test.yaml;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

public class DumpTest {
    @Test
    public void dump() {
        Map<String,Object> data = new HashMap<>();
        data.put("name", "zaccoding");
        data.put("race","human");
        data.put("hobbies",new String[]{"coding","game"});
        
        Yaml yaml = new Yaml();
        String output = yaml.dump(data);
        System.out.println(output);
    }
}
