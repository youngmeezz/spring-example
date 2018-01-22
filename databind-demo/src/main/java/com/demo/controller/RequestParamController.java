package com.demo.controller;

import com.demo.domain.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zaccoding
 * github : https://github.com/zacscoding
 * @Date : 2018-01-22
 */
@RestController
@RequestMapping("/request-param/**")
public class RequestParamController {
    private static final Logger logger = LoggerFactory.getLogger(RequestParamController.class);
    /* =============================================
     * HttpServletRequest
     =============================================*/
    @GetMapping("/param1")
    public String param1(HttpServletRequest req) {
        String value = req.getParameter("id");
        logger.info("## [request /param1] value : {}", value);
        return value;
    }

    /* =============================================
     * @RequestParam
     =============================================*/
    @GetMapping("/param2")
    public String param2(@RequestParam("id") String value) {
        logger.info("## [request /param2] value : {}", value);
        return value;
    }

    @GetMapping("/param3")
    public String param3(@RequestParam(value="id", required = false) String value) {
        logger.info("## [request /param3] value : {}", value);
        return value;
    }

    @GetMapping("/param4")
    public String param4(@RequestParam(value="id", defaultValue = "!defaultValue!") String value) {
        logger.info("## [request /param4] value : {}", value);
        return value;
    }

    @GetMapping("/person1")
    public String person1(@RequestParam(value="name") String name, @RequestParam("age") Integer age, @RequestParam("hobby") String hobby) {
        logger.info("## [request /person1] name : {}, age : {}, hobby : {}", name, age, hobby);
        return name + "(" + age + ") : " + hobby;
    }

    /* =============================================
     * Command Object
     =============================================*/
    @GetMapping("/person2")
    public String person2(Person person) {
        logger.info("## [request /person2] person : {}", person);
        return person.toString();
    }
}
