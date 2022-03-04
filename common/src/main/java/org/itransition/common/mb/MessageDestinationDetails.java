package org.itransition.common.mb;

import lombok.Getter;
import lombok.AllArgsConstructor;

/**
 * Provides core info for sending AMQP message to proper queue
 */
@Getter
@AllArgsConstructor
public class MessageDestinationDetails {

    private String exchangeName;
    private String routingKey;
}
