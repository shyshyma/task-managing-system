package org.itransition.audit.mq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.itransition.audit.constant.MessageBroker;
import org.itransition.audit.service.TaskLogService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Consumer {

    private final TaskLogService taskLogService;

    @RabbitListener(queues = MessageBroker.Queue.TASK_LOG_QUEUE_NAME)
    public void some(TaskLogMessage taskLogMessage) {
        log.info("Received message from the queue '{}'", MessageBroker.Queue.TASK_LOG_QUEUE_NAME);
        taskLogService.save(taskLogMessage);
    }
}
