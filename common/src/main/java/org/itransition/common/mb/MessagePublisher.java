package org.itransition.common.mb;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;

@Slf4j
@RequiredArgsConstructor
public class MessagePublisher {

    private final AmqpTemplate amqpTemplate;

    public void publish(Message message, MessageDestinationDetails details) {
        String exchangeName = details.getExchangeName();
        String routingKeyName = details.getRoutingKey();
        log.info("Sending message to exchange '{}' with routing key '{}'", exchangeName, routingKeyName);
        amqpTemplate.convertAndSend(exchangeName, routingKeyName, message);
    }
}
