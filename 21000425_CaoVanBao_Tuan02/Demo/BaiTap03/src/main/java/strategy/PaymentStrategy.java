package strategy;

import model.PaymentRequest;
import model.PaymentResult;

public interface PaymentStrategy {
    PaymentResult pay(PaymentRequest request);
    String getName();
}

