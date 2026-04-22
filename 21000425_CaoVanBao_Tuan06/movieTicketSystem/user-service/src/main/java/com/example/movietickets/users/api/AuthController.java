package com.example.movietickets.users.api;

import com.example.movietickets.common.events.EventEnvelope;
import com.example.movietickets.common.events.EventType;
import com.example.movietickets.common.rabbit.RabbitTopology;
import com.example.movietickets.users.rabbit.EventPublisher;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
  private final ConcurrentHashMap<String, String> users = new ConcurrentHashMap<>();
  private final EventPublisher publisher;

  public AuthController(EventPublisher publisher) {
    this.publisher = publisher;
  }

  public record AuthRequest(@NotBlank String username, @NotBlank String password) {}
  public record AuthResponse(String username, String token) {}
  public record ErrorResponse(String message) {}

  @PostMapping("/register")
  public ResponseEntity<?> register(@Valid @RequestBody AuthRequest req) {
    if (users.putIfAbsent(req.username(), req.password()) != null) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("Username already exists"));
    }

    var event = new EventEnvelope(
        UUID.randomUUID().toString(),
        EventType.USER_REGISTERED,
        Instant.now(),
        Map.of("username", req.username())
    );
    publisher.publish(RabbitTopology.EXCHANGE_EVENTS, RabbitTopology.ROUTING_USER_REGISTERED, event);

    return ResponseEntity.ok(new AuthResponse(req.username(), "token-" + req.username()));
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody AuthRequest req) {
    var stored = users.get(req.username());
    if (stored == null || !stored.equals(req.password())) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Invalid credentials"));
    }
    return ResponseEntity.ok(new AuthResponse(req.username(), "token-" + req.username()));
  }
}

