package org.itransition.audit.mq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.itransition.audit.service.AttachedFileService;
import org.itransition.audit.service.ConsumerLogService;
import org.itransition.audit.service.TaskLogService;
import org.itransition.common.mb.MessageBrokerConstant;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * Listens all log messages, which come
 * to this 'audit' microservice from RabbitMQ
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LogMessageListener {

    private final ConsumerLogService consumerLogService;
    private final TaskLogService taskLogService;
    private final AttachedFileService attachedFileService;

    @RabbitListener(queues = MessageBrokerConstant.Queue.CONSUMER_LOG_QUEUE_NAME)
    public void receiveConsumerLogMessage(@Payload ConsumerLogMessage consumerLogMessage) {
        log.info("Received message from the queue '{}'", MessageBrokerConstant.Queue.CONSUMER_LOG_QUEUE_NAME);
        consumerLogService.save(consumerLogMessage);
    }

    @RabbitListener(queues = MessageBrokerConstant.Queue.TASK_LOG_QUEUE_NAME)
    public void receiveTaskLogMessage(@Payload TaskLogMessage taskLogMessage) {
        log.info("Received message from the queue '{}'", MessageBrokerConstant.Queue.TASK_LOG_QUEUE_NAME);
        taskLogService.save(taskLogMessage);
    }

    @RabbitListener(queues = MessageBrokerConstant.Queue.ATTACHED_FILE_LOG_QUEUE_NAME)
    public void receiveAttachedFileLogMessage(@Payload AttachedFileLogMessage attachedFileLogMessage) {
        log.info("Received message from the queue '{}'", MessageBrokerConstant.Queue.ATTACHED_FILE_LOG_QUEUE_NAME);
        attachedFileService.save(attachedFileLogMessage);
    }
}
