package org.springdemo.web;

import org.springdemo.domain.EnvProfile;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    EnvProfile envProfile;
    @GetMapping("/home")
    public String index(Model model) {
        System.out.println(envProfile);
        model.addAttribute("title", "Spring Demo!!");
        return "index";
    }
}
