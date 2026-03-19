package state;

import decorator.PaymentProcessor;
import model.PaymentRequest;
import model.PaymentResult;

public interface PaymentState {
    PaymentResult handle(PaymentContext context, PaymentProcessor processor, PaymentRequest request);
    String getName();
}

