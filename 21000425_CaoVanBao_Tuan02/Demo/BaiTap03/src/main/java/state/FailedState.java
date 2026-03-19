package state;

import decorator.PaymentProcessor;
import model.PaymentRequest;
import model.PaymentResult;

public class FailedState implements PaymentState {
    @Override
    public PaymentResult handle(PaymentContext context, PaymentProcessor processor, PaymentRequest request) {
        System.out.println("Giao dịch bị THẤT BẠI.");
        return new PaymentResult(false, "Giao dịch thất bại", request.getAmount(), request.getAmount(), "Failed");
    }

    @Override
    public String getName() {
        return "Thất bại";
    }
}

