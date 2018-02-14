package org.springdemo.async;

import java.util.concurrent.Future;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springdemo.util.ServletUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

/**
 * @author zaccoding
 * github : https://github.com/zacscoding
 */
@Slf4j
@Component
public class MessageSender {

    @Async("asyncThreadPoolTaskExecutor")
    public Future<String> send(String message) {
        log.info("## MessageSender:send() message : {}", message);
        try {
            Thread.sleep(5000L);
            boolean isCommmited = true;
            HttpServletResponse res = ServletUtil.getHttpServletResponse();
            if (res != null) {
                isCommmited = res.isCommitted();
            }
            log.info("[## after sleep in MessageSender:send()] isCommited : " + isCommmited);
            return new AsyncResult<>(message);
        } catch (InterruptedException e) {
            log.error("## InterruptedException occur", e);
            return new AsyncResult<>("");
        }
    }
}
