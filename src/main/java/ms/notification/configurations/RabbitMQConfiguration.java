package ms.notification.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class RabbitMQConfiguration {

    private static final String ORIGINAL_EXCHANGE = "email";
    private static final String DLQ_EXCHANGE = ORIGINAL_EXCHANGE + ".dlq";
    private static final String ORIGINAL_QUEUE = "authentication.email.queue";
    private static final String DLQ_QUEUE = ORIGINAL_QUEUE + ".dlq";

    private final ConnectionFactory connectionFactory;

    @Bean
    DirectExchange directEmailExchange() {
        return ExchangeBuilder.directExchange(ORIGINAL_EXCHANGE).build();
    }

    @Bean
    DirectExchange dlqEmailExchange() {
        return ExchangeBuilder.directExchange(DLQ_EXCHANGE).build();
    }


    @Bean
    Queue queueEmail() {
        return QueueBuilder.durable(ORIGINAL_QUEUE)
            .deadLetterExchange(DLQ_EXCHANGE)
            .deadLetterRoutingKey(DLQ_QUEUE)
            .build();
//            .withArgument("x-dead-letter-exchange", dlqEmailExchange())
//            .withArgument("x-dead-letter-routing-key", dlqEmail()).build();
    }

    @Bean
    Queue dlqEmail() {
        return QueueBuilder
            .durable(DLQ_QUEUE)
            .build();
    }


    @Bean
    Binding bindQueueToExchange() {
        return BindingBuilder
            .bind(queueEmail())
            .to(directEmailExchange())
            .with(ORIGINAL_QUEUE);
    }

    @Bean
    Binding bindDlxQueueToExchange() {
        return BindingBuilder
            .bind(dlqEmail())
            .to(dlqEmailExchange())
            .with(DLQ_QUEUE);
    }


    @Bean
    public RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }


}
