package com.example.movietickets.bookings.rabbit;

import com.example.movietickets.bookings.domain.BookingRepository;
import com.example.movietickets.bookings.domain.BookingStatus;
import com.example.movietickets.common.events.EventEnvelope;
import com.example.movietickets.common.events.EventType;
import com.example.movietickets.common.rabbit.RabbitTopology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class BookingUpdatesConsumer {
  private static final Logger log = LoggerFactory.getLogger(BookingUpdatesConsumer.class);
  private final BookingRepository repo;

  public BookingUpdatesConsumer(BookingRepository repo) {
    this.repo = repo;
  }

  @RabbitListener(queues = RabbitTopology.QUEUE_BOOKING_UPDATES)
  public void onEvent(EventEnvelope event) {
    if (event == null || event.payload() == null || event.type() == null) return;

    if (event.type() == EventType.PAYMENT_COMPLETED) {
      Long bookingId = toLong(event.payload().get("bookingId"));
      if (bookingId != null) {
        repo.updateStatus(bookingId, BookingStatus.PAID);
        log.info("Booking #{} updated to PAID", bookingId);
      }
    } else if (event.type() == EventType.BOOKING_FAILED) {
      Long bookingId = toLong(event.payload().get("bookingId"));
      if (bookingId != null) {
        repo.updateStatus(bookingId, BookingStatus.FAILED);
        log.info("Booking #{} updated to FAILED", bookingId);
      }
    } else {
      log.debug("Ignored event type {}", event.type());
    }
  }

  private static Long toLong(Object o) {
    if (o == null) return null;
    if (o instanceof Integer i) return i.longValue();
    if (o instanceof Long l) return l;
    if (o instanceof Number n) return n.longValue();
    if (o instanceof String s && !s.isBlank()) {
      try {
        return Long.parseLong(s);
      } catch (NumberFormatException ignored) {
        return null;
      }
    }
    return null;
  }
}

