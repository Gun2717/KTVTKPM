package com.example.movietickets.users.rabbit;

import com.example.movietickets.common.events.EventEnvelope;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher {
  private final RabbitTemplate rabbitTemplate;

  public EventPublisher(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  public void publish(String exchange, String routingKey, EventEnvelope event) {
    rabbitTemplate.convertAndSend(exchange, routingKey, event, message -> {
      message.getMessageProperties().setContentType("application/json");
      return message;
    });
  }
}

