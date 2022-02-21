package org.itransition.taskmanager.mb;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Provides core info for sending AMQP message to proper queue
 */
@Getter
@AllArgsConstructor
public class MessageDestinationDetails {

    private String exchangeName;
    private String routingKey;
}
