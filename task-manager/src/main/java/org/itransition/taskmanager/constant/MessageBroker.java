package org.itransition.taskmanager.constant;

public final class MessageBroker {

    private static final String NOT_BE_INITIALIZED_EXCEPTION_MESSAGE = "This class cannot be initialized";

    private MessageBroker() {
        throw new RuntimeException(NOT_BE_INITIALIZED_EXCEPTION_MESSAGE);
    }

    public final class Queue {

        private Queue() {
            throw new RuntimeException(NOT_BE_INITIALIZED_EXCEPTION_MESSAGE);
        }

        public static final String TASK_LOG_QUEUE_NAME = "task-log-q";
    }


    public final class Exchange {

        private Exchange() {
            throw new RuntimeException(NOT_BE_INITIALIZED_EXCEPTION_MESSAGE);
        }

        public static final String TASK_LOG_EXCHANGE_NAME = "task-log-e";
    }


    public final class RoutingKey {

        private RoutingKey() {
            throw new RuntimeException(NOT_BE_INITIALIZED_EXCEPTION_MESSAGE);
        }

        public static final String TASK_LOG_ROUTING_KEY_NAME = "task-log-rk";
    }
}
