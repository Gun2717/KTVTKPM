package com.example.movietickets.bookings.rabbit;

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
public class BookingRabbitConfig {
  @Bean
  public Exchange eventsExchange() {
    return ExchangeBuilder.topicExchange(RabbitTopology.EXCHANGE_EVENTS).durable(true).build();
  }

  @Bean
  public Queue bookingUpdatesQueue() {
    return QueueBuilder.durable(RabbitTopology.QUEUE_BOOKING_UPDATES)
        .withArguments(Map.of(
            "x-dead-letter-exchange", RabbitTopology.EXCHANGE_EVENTS,
            "x-dead-letter-routing-key", "booking.updates.dlq"
        ))
        .build();
  }

  @Bean
  public Binding bindPaymentCompleted(Queue bookingUpdatesQueue, Exchange eventsExchange) {
    return BindingBuilder.bind(bookingUpdatesQueue).to(eventsExchange).with(RabbitTopology.ROUTING_PAYMENT_COMPLETED).noargs();
  }

  @Bean
  public Binding bindBookingFailed(Queue bookingUpdatesQueue, Exchange eventsExchange) {
    return BindingBuilder.bind(bookingUpdatesQueue).to(eventsExchange).with(RabbitTopology.ROUTING_BOOKING_FAILED).noargs();
  }
}

