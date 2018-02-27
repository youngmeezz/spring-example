package org.springchat.web;

import javax.servlet.http.HttpServletRequest;
import org.springchat.domain.Client;
import org.springchat.repository.ChatRepository;
import org.springchat.util.ServletUtil;
import org.springchat.util.SimpleLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import sun.java2d.pipe.SpanShapeRenderer.Simple;

/**
 * @author zaccoding
 * github : https://github.com/zacscoding
 */
@Controller
@RequestMapping("/chat")
public class ChatController {
//
//    @Autowired
//    private ChatRepository chatRepository;


    /**
     * Request to join chat & response Chat Room Information
     */
    @GetMapping("/join")
    @ResponseBody
    public DeferredResult<Void> join() {
        SimpleLogger.info("[## request join]");
        return null;
    }

    /**
     * Request to get messages
     * @return
     */
    @GetMapping("/messages")
    @ResponseBody
    public DeferredResult<Void> getMessages() {
        SimpleLogger.info("[## request get messages]");
        return null;
    }

    /**
     * Request to submit messages
     */
    @PostMapping("/messages")
    @ResponseBody
    public DeferredResult<Void> postMessage() {
        SimpleLogger.info("[## request submit message]");
        return null;
    }

    private Client getClientFromHttpServletRequest() {
        HttpServletRequest request = ServletUtil.getHttpServletRequest();
        if (request == null) {
            return null;
        }
        else {
            return null;
        }
    }
}
