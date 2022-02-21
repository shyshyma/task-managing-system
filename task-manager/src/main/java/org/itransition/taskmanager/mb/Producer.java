package org.itransition.taskmanager.mb;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Producer {

    private final AmqpTemplate amqpTemplate;

    public void send(Object message, MessageDestinationDetails details) {
        String exchangeName = details.getExchangeName();
        String routingKeyName = details.getRoutingKey();
        log.info("Sending message to exchange '{}' with routing key '{}'", exchangeName, routingKeyName);
        amqpTemplate.convertAndSend(exchangeName, routingKeyName, message);
    }
}
