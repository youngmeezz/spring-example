package org.springdemo.async;

import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springdemo.domain.User;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * ref :: https://spring.io/guides/gs/async-method/
 *
 * @author zacconding
 * @Date 2018-02-14
 * @GitHub : https://github.com/zacscoding
 */
@Component
@Slf4j
public class GithubLookupService {

    @Async("asyncThreadPoolTaskExecutor")
    public CompletableFuture<User> findUser(String user) throws InterruptedException {
        log.info("[## find user] user : {}, Thread id : {} , name : {}", user, Thread.currentThread().getId(), Thread.currentThread().getName());
        String url = String.format("https://api.github.com/users/%s", user);
        User result = new RestTemplate().getForObject(url, User.class);
        log.info("[## result of api] {}", result);
        Thread.sleep(5000L);
        log.info("##[after sleep]");
        return CompletableFuture.completedFuture(result);
    }
}
