package org.springchat.service;

import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author zaccoding
 * github : https://github.com/zacscoding
 */
public interface ChatService {
    public DeferredResult<String> getMessage();

    public void addMessage(String message);
}
