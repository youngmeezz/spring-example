package com.demo.controller;

import com.demo.util.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zaccoding
 * github : https://github.com/zacscoding
 * @Date : 2018-01-18
 */
@Controller
@RequestMapping("/pathvariable/**")
public class PathVariableController {
    private static final Logger logger = LoggerFactory.getLogger(PathVariableController.class);
    private static final String DEFAULT_VIEW = "home";

    // http://localhost:8080/data-bind/pathvariable/multiple/testParam1/mapping/testParam2
    @GetMapping(value="/multiple/{param1}/mapping/{param2}")
    public String defaultTest(@PathVariable("param1") String param1, @PathVariable("param2") String param2) {
        logger.info("## [request {}] param1 : {} , param2 : {}", ServletUtil.getRequestURI(), param1, param2);
        return DEFAULT_VIEW;
    }

    /**
     * mapping :: http://localhost:8080/data-bind/pathvariable/regex/a100
     * not found :: http://localhost:8080/data-bind/pathvariable/regex/a100a
     */
    // regex에 정규식을 사용하는 것보다는, Service 와같은 도메인 영역의 코드에서 param 값을 검사하는 것이 좋음
    @GetMapping(value="/regex/{param:[a-zA-Z]\\d\\d\\d}")
    public String regexTest(@PathVariable String param) {
        logger.info("## [request {}] param : {}", ServletUtil.getRequestURI(), param);
        return DEFAULT_VIEW;
    }
}
