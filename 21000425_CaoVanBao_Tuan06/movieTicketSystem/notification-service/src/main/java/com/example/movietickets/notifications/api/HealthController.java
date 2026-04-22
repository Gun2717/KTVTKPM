package com.example.movietickets.notifications.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
  @GetMapping("/")
  public String root() {
    return "notification-service";
  }
}

