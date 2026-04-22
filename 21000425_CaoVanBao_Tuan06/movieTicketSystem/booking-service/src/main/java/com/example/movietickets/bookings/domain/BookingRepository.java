package com.example.movietickets.bookings.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class BookingRepository {
  private final AtomicLong idSeq = new AtomicLong(0);
  private final ConcurrentHashMap<Long, Booking> store = new ConcurrentHashMap<>();

  public Booking create(String user, long movieId, List<String> seats) {
    long id = idSeq.incrementAndGet();
    Booking booking = new Booking(id, user, movieId, seats, BookingStatus.PENDING_PAYMENT, Instant.now());
    store.put(id, booking);
    return booking;
  }

  public Booking get(long id) {
    return store.get(id);
  }

  public List<Booking> list() {
    ArrayList<Booking> all = new ArrayList<>(store.values());
    all.sort(Comparator.comparingLong(Booking::id));
    return all;
  }

  public Booking updateStatus(long id, BookingStatus status) {
    return store.computeIfPresent(id, (k, old) -> new Booking(old.id(), old.user(), old.movieId(), old.seats(), status, old.createdAt()));
  }
}

