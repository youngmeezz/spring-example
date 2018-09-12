package org.demo.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zacconding
 * @Date 2018-09-12
 * @GitHub : https://github.com/zacscoding
 */
@Controller
@RequestMapping("/guide/**")
public class GuideController {

    private static final Logger logger = LoggerFactory.getLogger(GuideController.class);

    @GetMapping(value = "/first")
    @ResponseBody
    public ResponseEntity<String> first() {
        logger.info("## Request /guide/first GET.");
        return ResponseEntity.ok("SUCCESS");
    }
}