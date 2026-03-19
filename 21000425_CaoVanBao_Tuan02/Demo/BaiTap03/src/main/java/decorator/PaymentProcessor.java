package decorator;

import model.PaymentRequest;
import model.PaymentResult;

public interface PaymentProcessor {
    PaymentResult process(PaymentRequest request);
    String description();
}

