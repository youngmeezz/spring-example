package org.springdemo.controller;

import java.util.concurrent.Future;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springdemo.async.MessageSender;
import org.springdemo.util.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zaccoding
 * github : https://github.com/zacscoding
 */
@Controller
@RequestMapping("/async/**")
@Slf4j
public class AsyncController {

    @Autowired
    private MessageSender messageSender;

    @GetMapping("/echo/{message}")
    @ResponseBody
    public ResponseEntity<String> echo(@PathVariable("message") String message) {
        log.info("[## Request /async/echo] Current Thread ID : {}, Name : {}, message : {}", Thread.currentThread().getId(), Thread.currentThread().getName(), message);
        Future<String> future = messageSender.send(message);
        log.info("[## after invoke messageSender.send()]");

        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
