package com.example.movietickets.payments.rabbit;

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
public class PaymentRabbitConfig {
  @Bean
  public Exchange eventsExchange() {
    return ExchangeBuilder.topicExchange(RabbitTopology.EXCHANGE_EVENTS).durable(true).build();
  }

  @Bean
  public Queue paymentQueue() {
    return QueueBuilder.durable(RabbitTopology.QUEUE_PAYMENT)
        .withArguments(Map.of(
            "x-dead-letter-exchange", RabbitTopology.EXCHANGE_EVENTS,
            "x-dead-letter-routing-key", "payment.dlq"
        ))
        .build();
  }

  @Bean
  public Binding bindBookingCreated(Queue paymentQueue, Exchange eventsExchange) {
    return BindingBuilder.bind(paymentQueue).to(eventsExchange).with(RabbitTopology.ROUTING_BOOKING_CREATED).noargs();
  }
}

