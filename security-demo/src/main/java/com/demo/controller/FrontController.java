package com.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zacconding
 * @Date 2017-12-31
 * @GitHub : https://github.com/zacscoding
 */
@Controller
public class FrontController {
    @GetMapping("/403-errors")
    public String error403() {
        return "403";
    }
}
