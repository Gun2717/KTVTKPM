package com.example.movietickets.common.rabbit;

public final class RabbitTopology {
  private RabbitTopology() {}

  public static final String EXCHANGE_EVENTS = "mts.events";

  public static final String ROUTING_USER_REGISTERED = "user.registered";
  public static final String ROUTING_BOOKING_CREATED = "booking.created";
  public static final String ROUTING_PAYMENT_COMPLETED = "payment.completed";
  public static final String ROUTING_BOOKING_FAILED = "booking.failed";

  public static final String QUEUE_PAYMENT = "mts.payment.q";
  public static final String QUEUE_NOTIFICATION = "mts.notification.q";
  public static final String QUEUE_BOOKING_UPDATES = "mts.booking.updates.q";
}

