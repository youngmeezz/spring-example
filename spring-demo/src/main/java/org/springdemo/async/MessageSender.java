package org.springdemo.async;

import java.io.PrintWriter;
import java.util.concurrent.Future;
import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springdemo.util.EscapeUtil;
import org.springdemo.util.ThreadUtil;
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

    @Async("asyncThreadPoolTaskExecutor")
    public void sendRepeatedly(HttpServletRequest request, String message, int repeat, long sleep) {
        log.info("[## start async]  isAsyncStarted : {}, {}", request.isAsyncStarted(), ThreadUtil.getThreadInfo());
        if (request.isAsyncSupported()) {
            AsyncContext asyncContext = request.startAsync();
            try {
                final HttpServletResponse asyncResponse = (HttpServletResponse) asyncContext.getResponse();
                PrintWriter pw = asyncResponse.getWriter();
                for (int i = 0; i < repeat; i++) {
                    Thread.sleep(sleep);
                    String send = "<script type='text/javascript'>\n"
                        + "window.parent.result.append({ message: \""
                        + EscapeUtil.escape(message) + "\" });\n" + "</script>\n";
                    pw.println(send);
                    pw.flush();
                }

                asyncResponse.setStatus(HttpServletResponse.SC_OK);
                asyncContext.complete();
            } catch (Exception e) {
                log.error("[## failed to send]", e);
            }
        } else {
            log.info("[## not async supported]");
        }
    }


}
