package com.example.movietickets.users.rabbit;

import com.example.movietickets.common.rabbit.RabbitTopology;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserRabbitConfig {
  @Bean
  public Exchange eventsExchange() {
    return ExchangeBuilder.topicExchange(RabbitTopology.EXCHANGE_EVENTS).durable(true).build();
  }
}

