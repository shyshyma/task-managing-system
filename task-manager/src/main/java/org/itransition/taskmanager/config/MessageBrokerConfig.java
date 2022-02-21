package org.itransition.taskmanager.config;

import org.itransition.taskmanager.constant.MessageBroker;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MessageBrokerConfig {

    @Bean("taskLogQueue")
    public Queue queue() {
        return new Queue(MessageBroker.Queue.TASK_LOG_QUEUE_NAME, true, false, false);
    }

    @Bean("taskLogDirectExchange")
    public DirectExchange directExchange() {
        return new DirectExchange(MessageBroker.Exchange.TASK_LOG_EXCHANGE_NAME, true, false);
    }

    @Bean
    public Binding binding(@Qualifier("taskLogQueue") Queue queue,
                           @Qualifier("taskLogDirectExchange") DirectExchange exchange) {

        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(MessageBroker.RoutingKey.TASK_LOG_ROUTING_KEY_NAME);
    }

    @Primary
    @Bean("jacksonJsonMessageConverter")
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory factory, MessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(factory);
        template.setMessageConverter(converter);
        return template;
    }
}
