package demo.web;

import com.google.gson.GsonBuilder;
import demo.mapper.PersonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zacconding
 * @Date 2018-07-04
 * @GitHub : https://github.com/zacscoding
 */
@RestController
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    PersonMapper testMapper;

    @GetMapping("/")
    public String index() {
        return "index!";
    }

    @GetMapping("/find-all")
    public String findAll() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(testMapper.findAll());
    }
}
