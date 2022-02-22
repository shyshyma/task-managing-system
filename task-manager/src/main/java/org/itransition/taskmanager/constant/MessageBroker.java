package org.itransition.taskmanager.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class MessageBroker {

    @UtilityClass
    public final class Queue {
        public static final String TASK_LOG_QUEUE_NAME = "task-log-q";
    }

    @UtilityClass
    public final class Exchange {
        public static final String TASK_LOG_EXCHANGE_NAME = "task-log-e";
    }

    @UtilityClass
    public final class RoutingKey {
        public static final String TASK_LOG_ROUTING_KEY_NAME = "task-log-rk";
    }
}
