package com.demo.controller;

import com.demo.domain.Person;
import com.demo.util.GsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author zaccoding
 * github : https://github.com/zacscoding
 * @Date : 2018-01-22
 */
@Controller
@RequestMapping("/data-bind/**")
public class DataBindController {
    private static final Logger logger = LoggerFactory.getLogger(DataBindController.class);
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @PostMapping("/person")
    public String binding1(Person person, RedirectAttributes rttr) {
        logger.info("## request /person hobbies size : {} , person : \n{}", person.getHobbies().size(), GsonUtil.prettyPrint(person));
        rttr.addFlashAttribute("message", person.toString());
        return "redirect:index";
    }
}
