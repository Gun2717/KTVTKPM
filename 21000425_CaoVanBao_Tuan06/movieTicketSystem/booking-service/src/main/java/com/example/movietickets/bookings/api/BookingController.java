package com.example.movietickets.bookings.api;

import com.example.movietickets.bookings.domain.Booking;
import com.example.movietickets.bookings.domain.BookingRepository;
import com.example.movietickets.common.events.EventEnvelope;
import com.example.movietickets.common.events.EventType;
import com.example.movietickets.common.rabbit.RabbitTopology;
import com.example.movietickets.bookings.rabbit.EventPublisher;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {
  private final BookingRepository repo;
  private final EventPublisher publisher;

  public BookingController(BookingRepository repo, EventPublisher publisher) {
    this.repo = repo;
    this.publisher = publisher;
  }

  public record CreateBookingRequest(
      @NotBlank String user,
      @Min(1) long movieId,
      @NotEmpty List<@NotBlank String> seats
  ) {}

  @GetMapping("/bookings")
  public List<Booking> list() {
    return repo.list();
  }

  @PostMapping("/bookings")
  public ResponseEntity<Booking> create(@Valid @RequestBody CreateBookingRequest req) {
    Booking booking = repo.create(req.user(), req.movieId(), req.seats());

    EventEnvelope event = new EventEnvelope(
        UUID.randomUUID().toString(),
        EventType.BOOKING_CREATED,
        Instant.now(),
        Map.of(
            "bookingId", booking.id(),
            "user", booking.user(),
            "movieId", booking.movieId(),
            "seats", booking.seats()
        )
    );
    publisher.publish(RabbitTopology.EXCHANGE_EVENTS, RabbitTopology.ROUTING_BOOKING_CREATED, event);

    return ResponseEntity.status(HttpStatus.CREATED).body(booking);
  }
}

