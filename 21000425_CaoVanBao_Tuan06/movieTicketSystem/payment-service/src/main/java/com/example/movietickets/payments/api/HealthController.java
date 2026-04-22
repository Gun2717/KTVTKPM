package com.example.movietickets.payments.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
  @GetMapping("/")
  public String root() {
    return "payment-service";
  }
}

