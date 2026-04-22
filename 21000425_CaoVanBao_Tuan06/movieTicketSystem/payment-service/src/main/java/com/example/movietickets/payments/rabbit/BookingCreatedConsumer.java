package com.example.movietickets.payments.rabbit;

import com.example.movietickets.common.events.EventEnvelope;
import com.example.movietickets.common.events.EventType;
import com.example.movietickets.common.rabbit.RabbitTopology;
import java.time.Instant;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class BookingCreatedConsumer {
  private static final Logger log = LoggerFactory.getLogger(BookingCreatedConsumer.class);
  private final EventPublisher publisher;
  private final Random random = new Random();

  public BookingCreatedConsumer(EventPublisher publisher) {
    this.publisher = publisher;
  }

  @RabbitListener(queues = RabbitTopology.QUEUE_PAYMENT)
  public void onBookingCreated(EventEnvelope event) {
    if (event == null || event.payload() == null || event.type() != EventType.BOOKING_CREATED) return;

    Object bookingId = event.payload().get("bookingId");
    Object user = event.payload().get("user");

    boolean success = random.nextBoolean();
    EventType outType = success ? EventType.PAYMENT_COMPLETED : EventType.BOOKING_FAILED;
    String routingKey = success ? RabbitTopology.ROUTING_PAYMENT_COMPLETED : RabbitTopology.ROUTING_BOOKING_FAILED;

    EventEnvelope out = new EventEnvelope(
        UUID.randomUUID().toString(),
        outType,
        Instant.now(),
        Map.of(
            "bookingId", bookingId,
            "user", user,
            "message", success ? "Payment success" : "Payment failed"
        )
    );

    publisher.publish(RabbitTopology.EXCHANGE_EVENTS, routingKey, out);
    log.info("Processed bookingId={} => {}", bookingId, outType);
  }
}

