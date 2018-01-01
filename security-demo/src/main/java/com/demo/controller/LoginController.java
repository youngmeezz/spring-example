package com.demo.controller;

import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zacconding
 * @Date 2017-12-31
 * @GitHub : https://github.com/zacscoding
 */
@Controller
@Log
public class LoginController {
    @GetMapping({"/login","/loginPage"})
    public String loginPage() {
        System.out.println("## login page request");
        return "loginPage";
    }

    @GetMapping("/loginPOST")
    public String loginFail() {
        return "loginPage";
    }
}
