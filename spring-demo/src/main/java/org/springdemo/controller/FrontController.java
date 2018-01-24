package org.springdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zacconding
 * @Date 2018-01-25
 * @GitHub : https://github.com/zacscoding
 */
@Controller
public class FrontController {
    @GetMapping("/home")
    public String index(Model model) {
        model.addAttribute("title", "Spring Demo!!");

        return "index";
    }
}
