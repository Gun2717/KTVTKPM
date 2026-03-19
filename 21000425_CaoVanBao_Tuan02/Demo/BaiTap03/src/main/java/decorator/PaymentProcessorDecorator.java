package decorator;

import model.PaymentRequest;
import model.PaymentResult;

public abstract class PaymentProcessorDecorator implements PaymentProcessor {
    protected final PaymentProcessor wrappee;

    protected PaymentProcessorDecorator(PaymentProcessor wrappee) {
        this.wrappee = wrappee;
    }

    @Override
    public PaymentResult process(PaymentRequest request) {
        return wrappee.process(request);
    }

    @Override
    public String description() {
        return wrappee.description();
    }
}

