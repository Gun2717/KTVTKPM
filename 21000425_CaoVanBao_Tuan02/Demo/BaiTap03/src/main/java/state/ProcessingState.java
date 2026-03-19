package state;

import decorator.PaymentProcessor;
import model.PaymentRequest;
import model.PaymentResult;

public class ProcessingState implements PaymentState {
    @Override
    public PaymentResult handle(PaymentContext context, PaymentProcessor processor, PaymentRequest request) {
        System.out.println("Đang xử lý thanh toán...");
        PaymentResult result = processor.process(request);
        if (result.success()) {
            context.setState(new CompletedState());
        } else {
            context.setState(new FailedState());
        }
        return context.getState().handle(context, processor, request);
    }

    @Override
    public String getName() {
        return "Đang xử lý";
    }
}

