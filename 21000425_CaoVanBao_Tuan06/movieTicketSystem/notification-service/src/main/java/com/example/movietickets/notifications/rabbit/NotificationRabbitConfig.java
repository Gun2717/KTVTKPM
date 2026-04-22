package com.example.movietickets.notifications.rabbit;

import com.example.movietickets.common.rabbit.RabbitTopology;
import java.util.Map;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationRabbitConfig {
  @Bean
  public Exchange eventsExchange() {
    return ExchangeBuilder.topicExchange(RabbitTopology.EXCHANGE_EVENTS).durable(true).build();
  }

  @Bean
  public Queue notificationQueue() {
    return QueueBuilder.durable(RabbitTopology.QUEUE_NOTIFICATION)
        .withArguments(Map.of(
            "x-dead-letter-exchange", RabbitTopology.EXCHANGE_EVENTS,
            "x-dead-letter-routing-key", "notification.dlq"
        ))
        .build();
  }

  @Bean
  public Binding bindPaymentCompleted(Queue notificationQueue, Exchange eventsExchange) {
    return BindingBuilder.bind(notificationQueue).to(eventsExchange).with(RabbitTopology.ROUTING_PAYMENT_COMPLETED).noargs();
  }

  @Bean
  public Binding bindBookingFailed(Queue notificationQueue, Exchange eventsExchange) {
    return BindingBuilder.bind(notificationQueue).to(eventsExchange).with(RabbitTopology.ROUTING_BOOKING_FAILED).noargs();
  }
}

