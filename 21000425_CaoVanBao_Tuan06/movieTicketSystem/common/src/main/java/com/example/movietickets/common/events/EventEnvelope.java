package com.example.movietickets.common.events;

import java.time.Instant;
import java.util.Map;

public record EventEnvelope(
    String id,
    EventType type,
    Instant occurredAt,
    Map<String, Object> payload
) {}

