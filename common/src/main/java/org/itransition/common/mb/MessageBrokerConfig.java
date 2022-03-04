package org.itransition.common.mb;

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

    @Bean
    public DirectExchange logDirectExchange() {
        return new DirectExchange(MessageBrokerConstant.Exchange.LOG_EXCHANGE_NAME, true, false);
    }

    @Bean
    public Queue consumerLogQueue() {
        return new Queue(MessageBrokerConstant.Queue.CONSUMER_LOG_QUEUE_NAME, true, false, false);
    }

    @Bean
    public Binding consumerLogBinding(@Qualifier("consumerLogQueue") Queue queue,
                                      @Qualifier("logDirectExchange") DirectExchange exchange) {

        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(MessageBrokerConstant.RoutingKey.CONSUMER_LOG_ROUTING_KEY_NAME);
    }

    @Bean
    public Queue taskLogQueue() {
        return new Queue(MessageBrokerConstant.Queue.TASK_LOG_QUEUE_NAME, true, false, false);
    }

    @Bean
    public Binding taskLogBinding(@Qualifier("taskLogQueue") Queue queue,
                                  @Qualifier("logDirectExchange") DirectExchange exchange) {

        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(MessageBrokerConstant.RoutingKey.TASK_LOG_ROUTING_KEY_NAME);
    }

    @Bean
    public Queue attachedFileLogQueue() {
        return new Queue(MessageBrokerConstant.Queue.ATTACHED_FILE_LOG_QUEUE_NAME, true, false, false);
    }

    @Bean
    public Binding attachedFileLogBinding(@Qualifier("attachedFileLogQueue") Queue queue,
                                          @Qualifier("logDirectExchange") DirectExchange exchange) {

        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(MessageBrokerConstant.RoutingKey.ATTACHED_FILE_LOG_ROUTING_KEY_NAME);
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

    @Bean
    public MessagePublisher messagePublisher(AmqpTemplate amqpTemplate) {
        return new MessagePublisher(amqpTemplate);
    }
}
