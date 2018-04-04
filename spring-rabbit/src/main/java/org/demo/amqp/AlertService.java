package org.demo.amqp;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ref : Spring in action
 */

@Service
public class AlertService {

    private static final Logger logger = LoggerFactory.getLogger(AlertService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendSpittleAlert(Spittle spittle) {
        try {
            // param :: name of the exchange , routing key , object
            // Object(Spittle) --> SimpleMessageConverter --> org.springframework.amqp.core.Message
            // rabbitTemplate.convertAndSend("spittle.alert.exchange", "spittle.alerts", spittle);

            /*  leave out exchange name or routing key => using default name    */
            // param :: routing key, object => leave out the exchange name
            // rabbitTemplate.convertAndSend("spittle.alerts", new Gson().toJson(spittle));
            // param :: object => leave out both the exchange name and routing key
            rabbitTemplate.convertAndSend(new Gson().toJson(spittle));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
