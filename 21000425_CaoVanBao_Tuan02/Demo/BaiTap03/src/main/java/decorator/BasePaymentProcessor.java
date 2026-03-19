package decorator;

import model.PaymentRequest;
import model.PaymentResult;
import strategy.PaymentStrategy;

public class BasePaymentProcessor implements PaymentProcessor {
    private final PaymentStrategy strategy;

    public BasePaymentProcessor(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public PaymentResult process(PaymentRequest request) {
        return strategy.pay(request);
    }

    @Override
    public String description() {
        return strategy.getName();
    }
}

