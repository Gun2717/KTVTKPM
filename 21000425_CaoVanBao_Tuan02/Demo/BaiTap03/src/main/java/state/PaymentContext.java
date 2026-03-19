package state;

import decorator.PaymentProcessor;
import model.PaymentRequest;
import model.PaymentResult;

public class PaymentContext {
    private PaymentState state;

    public PaymentContext() {
        this.state = new InitState();
    }

    public PaymentState getState() {
        return state;
    }

    public void setState(PaymentState state) {
        this.state = state;
    }

    public PaymentResult process(PaymentProcessor processor, PaymentRequest request) {
        System.out.println("== Đơn " + request.getOrderId() + " | Trạng thái: " + state.getName());
        return state.handle(this, processor, request);
    }
}

