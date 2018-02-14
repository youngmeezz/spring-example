package org.springdemo.async;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springdemo.util.ServletUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

/**
 * @author zaccoding github : https://github.com/zacscoding
 */
@Slf4j
@Component
public class MessageSender {

    @Async("asyncThreadPoolTaskExecutor")
    public Future<String> send(String message) {
        log.info("[## MessageSender:send()] message : {}, Thread id : {}, name : {}", message, Thread.currentThread().getId(), Thread.currentThread().getName());
        try {
            // work
            Thread.sleep(5000L);
            
            return new AsyncResult<>(message);
        } catch (InterruptedException e) {
            log.error("## InterruptedException occur", e);
            return new AsyncResult<>("");
        }
    }
}
