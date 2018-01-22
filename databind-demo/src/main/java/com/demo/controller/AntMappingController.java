package com.demo.controller;

import com.demo.util.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 *
 * @author zaccoding
 * github : https://github.com/zacscoding
 * @Date : 2018-01-18
 */
@Controller
@RequestMapping("/ant")
public class AntMappingController {
    private static final Logger logger = LoggerFactory.getLogger(AntMappingController.class);
    private static final String DEFAULT_VIEW = "home";

    /*================= ant pattern test =================
    * :: 0개 또는 그 이상의 글자
    ? :: 1개 글자
    ** :: 0개 또는 그 이상의 디렉터리 경로

    == pattern : ?*.info ==
    /ant/123.info

    == not found ==
    /ant/.info

    == pattern : f?00.fq ==
    /ant/fa00.fq

    == not found ==
    /ant/f00.fq

    == pattern : ** /files ==
    /ant/test1/test2/files
    /ant//files
    ==================================================== */
    @GetMapping("/?*.info")
    public String antPattern() {
        return handleAntPattern("?*.info");
    }

    @GetMapping("/f?00.fq")
    public String antPattern2() {
        return handleAntPattern("f?00.fq");
    }

    @GetMapping("/**/files")
    public String antPattern3() {
        return handleAntPattern("**/files");
    }

    private String handleAntPattern(String pattern) {
        logger.info("## [request : {}, pattern : {}]", ServletUtil.getRequestURI(), pattern);
        return DEFAULT_VIEW;
    }
}
