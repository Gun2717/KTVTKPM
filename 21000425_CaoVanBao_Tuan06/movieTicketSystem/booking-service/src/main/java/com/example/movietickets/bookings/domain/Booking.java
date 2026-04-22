package com.example.movietickets.bookings.domain;

import java.time.Instant;
import java.util.List;

public record Booking(
    long id,
    String user,
    long movieId,
    List<String> seats,
    BookingStatus status,
    Instant createdAt
) {}

