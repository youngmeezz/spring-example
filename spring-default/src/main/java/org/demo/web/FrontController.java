package org.demo.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author zacconding
 * @Date 2018-01-16
 * @GitHub : https://github.com/zacscoding
 */

@Controller
public class FrontController {
    private static final Logger logger = LoggerFactory.getLogger(FrontController.class);

    @GetMapping(value="/")
    public String index(Model model) {
        logger.info("## [request index page]");
        model.addAttribute("title", "sample project");
        return "index";
    }

    @PostMapping(value="/rest", produces = "application/json; charset=utf8")
    public @ResponseBody String rest(@RequestBody String param) {
        logger.info("## [request rest]");
        return param;
    }
}
