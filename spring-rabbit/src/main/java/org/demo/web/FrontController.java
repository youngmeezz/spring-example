package org.demo.web;

import java.util.Date;
import java.util.Random;
import org.demo.amqp.AlertService;
import org.demo.amqp.Spittle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zacconding
 * @Date 2018-04-04
 * @GitHub : https://github.com/zacscoding
 */
@Controller
public class FrontController {

    private static final Logger logger = LoggerFactory.getLogger(FrontController.class);

    @Autowired
    private AlertService alertService;

    @GetMapping(value = "/")
    public String index(Model model) {
        logger.info("## request index page..");
        model.addAttribute("title", "sample");
        return "index";
    }

    @GetMapping(value = "/produce")
    @ResponseBody
    public String produce() {
        Spittle spittle = new Spittle("Message" + new Random().nextInt(100), new Date());
        alertService.sendSpittleAlert(spittle);
        return spittle.toString();
    }
}
