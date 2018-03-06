package org.springchat.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springchat.util.SimpleLogger;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author zaccoding
 * github : https://github.com/zacscoding
 */
@Service
public class ChatServiceImpl implements ChatService {

    private List<DeferredResult<String>> chatRequests = Collections.synchronizedList(new ArrayList<DeferredResult<String>>());

    @Override
    public DeferredResult<String> getMessage() {
        final DeferredResult<String> result = new DeferredResult<String>(null, null);

        chatRequests.add(result);

        result.onCompletion(() -> {
            chatRequests.remove(result);
        });

        return result;
    }

    @Override
    public void addMessage(String message) {
        if (!chatRequests.isEmpty()) {
            for (DeferredResult<String> result : chatRequests) {
                result.setResult(message);
            }
        } else {
            SimpleLogger.info("[## not exist chat requests in list]");
        }
    }
}
