package com.example.movietickets.notifications.rabbit;

import com.example.movietickets.common.events.EventEnvelope;
import com.example.movietickets.common.events.EventType;
import com.example.movietickets.common.rabbit.RabbitTopology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {
  private static final Logger log = LoggerFactory.getLogger(NotificationConsumer.class);

  @RabbitListener(queues = RabbitTopology.QUEUE_NOTIFICATION)
  public void onEvent(EventEnvelope event) {
    if (event == null || event.type() == null || event.payload() == null) return;

    Object bookingId = event.payload().get("bookingId");
    Object user = event.payload().get("user");

    if (event.type() == EventType.PAYMENT_COMPLETED) {
      log.info("Booking #{} thành công! (user={})", bookingId, user);
    } else if (event.type() == EventType.BOOKING_FAILED) {
      log.info("Booking #{} thất bại! (user={})", bookingId, user);
    } else {
      log.debug("Ignored event type {}", event.type());
    }
  }
}

