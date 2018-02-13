package com.demo.controller;

import com.demo.domain.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zacconding
 * @Date 2018-01-08
 * @GitHub : https://github.com/zacscoding
 */
@Controller
public class FrontController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "title!@!");
        return "home";
    }

    @GetMapping("/value")
    public String test(Model model) {
        Person p = new Person();
        p.setAge(10);
        p.setName(";\\");
        model.addAttribute("person", p);

        return "home";
    }
}
