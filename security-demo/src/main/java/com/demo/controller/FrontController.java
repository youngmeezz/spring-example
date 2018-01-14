package com.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @GetMapping("/local-test")
    public String localDateTimeTest(Model model) {
        model.addAttribute("format", DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
        model.addAttribute("now", LocalDateTime.now());
        return "dateTest";
    }
}
