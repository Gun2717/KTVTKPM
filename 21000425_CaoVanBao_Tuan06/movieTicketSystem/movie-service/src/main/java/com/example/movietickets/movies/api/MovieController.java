package com.example.movietickets.movies.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {
  private final AtomicLong idSeq = new AtomicLong(0);
  private final ConcurrentHashMap<Long, Movie> store = new ConcurrentHashMap<>();

  public record Movie(long id, String title) {}
  public record UpsertMovieRequest(@NotBlank String title) {}
  public record ErrorResponse(String message) {}

  @GetMapping("/movies")
  public List<Movie> list() {
    ArrayList<Movie> all = new ArrayList<>(store.values());
    all.sort(Comparator.comparingLong(Movie::id));
    return all;
  }

  @PostMapping("/movies")
  public ResponseEntity<Movie> create(@Valid @RequestBody UpsertMovieRequest req) {
    long id = idSeq.incrementAndGet();
    Movie movie = new Movie(id, req.title());
    store.put(id, movie);
    return ResponseEntity.status(HttpStatus.CREATED).body(movie);
  }

  @PutMapping("/movies/{id}")
  public ResponseEntity<?> update(@PathVariable("id") long id, @Valid @RequestBody UpsertMovieRequest req) {
    Movie existing = store.get(id);
    if (existing == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Movie not found"));
    }
    Movie updated = new Movie(id, req.title());
    store.put(id, updated);
    return ResponseEntity.ok(updated);
  }
}

