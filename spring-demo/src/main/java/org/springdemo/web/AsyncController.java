package org.springdemo.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springdemo.async.GithubLookupService;
import org.springdemo.async.MessageSender;
import org.springdemo.domain.User;
import org.springdemo.util.EscapeUtil;
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
 * @author zaccoding github : https://github.com/zacscoding
 */
@Controller
@RequestMapping("/async/**")
@Slf4j
public class AsyncController {

    @Autowired
    private MessageSender messageSender;
    @Autowired
    private GithubLookupService githubLookupService;

    @GetMapping("/echo")
    public String echo() {
        return "echo";
    }

    @GetMapping("/echo/{message}/{isWait}")
    @ResponseBody
    public ResponseEntity<String> echo(@PathVariable("message") String message, @PathVariable("isWait") boolean isWait) {
        log.info("[## Request /async/echo] Current Thread ID : {}, Name : {}, message : {}", Thread.currentThread().getId(), Thread.currentThread().getName(), message);
        Future<String> future = messageSender.send(message);
        log.info("[## after invoke messageSender.send()]");
        if (isWait && future != null) {
            try {
                // wait
                String result = null;
                while ((result = future.get()) == null) {
                }
                message += result;
            } catch (Exception e) {
                message += ("Exception occur : " + e.getMessage());
            }
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/echo/{message}/{repeat}/{sleep}")
    @ResponseBody
    public void echoRepeatedly(@PathVariable("message") String message, @PathVariable("repeat") int repeat, @PathVariable("sleep") long sleep) throws Exception {
        log.info("[## MessageSender:sendRepeatedly()] message : {}, repeat : {}, sleep : {}, Thread id : {}, name : {}", message, repeat, sleep, Thread.currentThread().getId(),Thread.currentThread().getName());
        HttpServletRequest request = ServletUtil.getHttpServletRequest();
        messageSender.sendRepeatedly(request, message, repeat, sleep);
    }

    @GetMapping("/github-lookup")
    public String githubLookupIndex() {
        return "githubLookup";
    }

    @GetMapping("/github-lookup/{user}")
    @ResponseBody
    public void githubLookup(@PathVariable("user") String user) throws IOException {
        HttpServletResponse res = ServletUtil.getHttpServletResponse();
        PrintWriter pw = res.getWriter();
        try {
            log.info("[## request github-lookup] user : {}, Thread id : {} , name : {}", user, Thread.currentThread().getId(), Thread.currentThread().getName());
            CompletableFuture<User> result = githubLookupService.findUser(user);
            log.info("[## after githubLookupService.findUser()] result.isDone() : {}", result.isDone());
            res.setContentType("text/html; charset=UTF-8");
            res.setHeader("Cache-Control", "private");
            res.setHeader("Pragma", "no-cache");
            res.setCharacterEncoding("UTF-8");

            // send complete request message
            sendMessage(pw, "Success request");
            // wait
            //CompletableFuture.allOf(result).join();
            // send result
            sendMessage(pw, result.get().toString());
            sendMessage(pw, "Complete request");
        } catch (Exception e) {
            sendMessage(pw, e.getMessage());
        }
    }

    private void sendMessage(PrintWriter pw, String message) throws IOException {
        if (pw == null) {
            return;
        }
        pw.println(parseContent(message));
        pw.flush();
    }

    /**
     * http://javacan.tistory.com/entry/Servlet-3-Async parse JS append command
     */
    private String parseContent(String message) {
        return "<script type='text/javascript'>\n"
            + "window.parent.result.append({ message: \""
            + EscapeUtil.escape(message) + "\" });\n" + "</script>\n";
    }
}
