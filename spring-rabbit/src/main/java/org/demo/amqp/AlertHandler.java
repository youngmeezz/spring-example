package org.demo.amqp;

import org.demo.util.SimpleLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author zacconding
 * @Date 2018-04-04
 * @GitHub : https://github.com/zacscoding
 */
@Component
public class AlertHandler {

    private static final Logger logger = LoggerFactory.getLogger(AlertHandler.class);

    public AlertHandler() {
        SimpleLogger.println("## created alert handler instance");
    }

    @RabbitListener(queues = "myQueue")
    public void handleSpittleAlert(String spittle) {
        SimpleLogger.build()
                    .append("##").appendRepeat(10,"==").append(" receive spittle ").appendRepeat(10,"==").appendln("##")
                    .appendln("## {}", SimpleLogger.getThreadInfo())
                    .appendln(spittle)
                    .flush();
    }
}
