package org.springdemo.web;

import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.stream.IntStream;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springdemo.domain.Person;
import org.springdemo.util.ServletUtil;
import org.springdemo.util.ThreadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;

/**
 * Async Return test ref :: http://wonwoo.ml/index.php/post/1912
 *
 * @author zacconding
 * @Date 2018-02-26
 * @GitHub : https://github.com/zacscoding
 */
@RestController
@RequestMapping("/async-return")
@Slf4j
@EnableScheduling
public class AsyncReturnController {

    @Autowired
    private Executor asyncThreadPoolTaskExecutor;

    // http://localhost:8080/demo/async-return/callable
    @GetMapping("/callable")
    public Callable<Person> person() {
        HttpServletRequest httpServletRequest = ServletUtil.getHttpServletRequest();
        log.info("[## request /callable] isAsyncStarted : {}, {}", httpServletRequest.isAsyncStarted(), ThreadUtil.getThreadInfo());
        return () -> {
            try {
                System.out.println(ServletUtil.getHttpServletRequest());
                log.info("[## in Callable] isAsyncStarted : {}, {}", httpServletRequest.isAsyncStarted(), ThreadUtil.getThreadInfo());
                Thread.sleep(3000L);
                System.out.println("after sleep 3000");
            } catch (InterruptedException e) {
            }

            return getPerson();
        };
    }

    // http://localhost:8080/demo/async-return/future
    @GetMapping("/future")
    public CompletableFuture<Person> future() {
        HttpServletRequest httpServletRequest = ServletUtil.getHttpServletRequest();
        log.info("[## request /future] isAsyncStarted : {}, {}", httpServletRequest.isAsyncStarted(), ThreadUtil.getThreadInfo());
        return CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(ServletUtil.getHttpServletRequest());
                log.info("[## in CompletableFuture] isAsyncStarted : {}, {}", httpServletRequest.isAsyncStarted(), ThreadUtil.getThreadInfo());
                Thread.sleep(3000L);
                System.out.println("after sleep 3000");
            } catch (InterruptedException e) {
            }

            return getPerson();
        });
    }

    // http://localhost:8080/demo/async-return/webAsyncTask
    @GetMapping("/webAsyncTask")
    public WebAsyncTask<Person> webAsyncTask() {
        HttpServletRequest httpServletRequest = ServletUtil.getHttpServletRequest();
        log.info("[## request /webAsyncTask] isAsyncStarted : {}, {}", httpServletRequest.isAsyncStarted(), ThreadUtil.getThreadInfo());
        return new WebAsyncTask<>(1000L, () -> {
            try {
                System.out.println(ServletUtil.getHttpServletRequest());
                log.info("[## in WebAsyncTask] isAsyncStarted : {}, {}", httpServletRequest.isAsyncStarted(), ThreadUtil.getThreadInfo());
                Thread.sleep(3000L);
                System.out.println("after sleep 3000");
            } catch (InterruptedException e) {
            }
            return getPerson();
        });
    }

    private final Queue<DeferredResult<Person>> personsQueue = new ConcurrentLinkedQueue<>();

    // http://localhost:8080/demo/async-return/deferred
    @GetMapping("/deferred")
    public DeferredResult<Person> persons() {
        HttpServletRequest httpServletRequest = ServletUtil.getHttpServletRequest();
        log.info("[## request /deferred] isAsyncStarted : {}, {}", httpServletRequest.isAsyncStarted(), ThreadUtil.getThreadInfo());
        DeferredResult<Person> result = new DeferredResult<>();
        personsQueue.add(result);
        result.setResult(getPerson());
        return result;
    }

    @Scheduled(fixedRate = 1000L)
    public void processQueue() {
        for (DeferredResult<Person> result : this.personsQueue) {
            log.info("setResult...");
            result.setResult(getPerson());
            personsQueue.remove(result);
        }
    }


    private Person getPerson() {
        return new Person((int) (Math.random() * 10), "name", 22);
    }


}
