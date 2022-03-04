package org.itransition.common.mb;

import lombok.experimental.UtilityClass;

/**
 * Constants for queues, exchanges and bindings for RabbitMQ
 */
@UtilityClass
public final class MessageBrokerConstant {

    @UtilityClass
    public final class Queue {

        public static final String CONSUMER_LOG_QUEUE_NAME = "consumer-log-q";
        public static final String TASK_LOG_QUEUE_NAME = "task-log-q";
        public static final String ATTACHED_FILE_LOG_QUEUE_NAME = "attached-file-log-q";
    }

    @UtilityClass
    public final class Exchange {

        public static final String LOG_EXCHANGE_NAME = "log-e";
    }

    @UtilityClass
    public final class RoutingKey {

        public static final String CONSUMER_LOG_ROUTING_KEY_NAME = "consumer-log-rk";
        public static final String TASK_LOG_ROUTING_KEY_NAME = "task-log-rk";
        public static final String ATTACHED_FILE_LOG_ROUTING_KEY_NAME = "attached-file-log-rk";
    }
}
