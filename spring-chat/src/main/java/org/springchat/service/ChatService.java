package org.springchat.service;

import java.util.PriorityQueue;
import java.util.Queue;
import org.springchat.domain.ChatRequest;
import org.springchat.domain.ChatRoom;
import org.springchat.domain.Client;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author zaccoding
 * github : https://github.com/zacscoding
 */
@Service
public class ChatService {

    private Queue<ChatRequest> awaiterQueue = new PriorityQueue<>();

    public DeferredResult<ChatRoom> join(Client client) {
        final DeferredResult<ChatRoom> deferredResult = new DeferredResult<>(null, null);
        ChatRequest request = new ChatRequest(client, deferredResult);
        if (awaiterQueue.isEmpty()) {
            awaiterQueue.add(request);
        }
        else {
            ChatRequest another = awaiterQueue.poll();
            // todo...
        }

        return deferredResult;
    }
}
