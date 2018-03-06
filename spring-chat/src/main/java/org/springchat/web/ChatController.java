package org.springchat.web;

import org.springchat.service.ChatService;
import org.springchat.util.SimpleLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

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
    @Autowired
    private ChatService chatService;

    @GetMapping("/index")
    public String index() {
        SimpleLogger.println("## [request index page]");
        return "index";
    }

    /**
     * Request to join chat & response Chat Room Information
     */
    @GetMapping("/messages")
    @ResponseBody
    public DeferredResult<String> getMessages() {
        SimpleLogger.info("[## request get messages]");
        return chatService.getMessage();
    }

    @PostMapping("/messages")
    @ResponseBody
    public void postMessage(@RequestBody String message) {
        SimpleLogger.info("[## request post message] message : " + message);
        chatService.addMessage(message);
    }
}
