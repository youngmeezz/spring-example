package org.demo.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zacconding
 * @Date 2018-09-12
 * @GitHub : https://github.com/zacscoding
 */
@Controller
@RequestMapping("/person/**")
public class PersonController {

    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

}