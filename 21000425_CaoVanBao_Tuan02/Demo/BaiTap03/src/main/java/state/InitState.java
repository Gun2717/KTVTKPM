package state;

import decorator.PaymentProcessor;
import model.PaymentRequest;
import model.PaymentResult;

public class InitState implements PaymentState {
    @Override
    public PaymentResult handle(PaymentContext context, PaymentProcessor processor, PaymentRequest request) {
        System.out.println("Khởi tạo giao dịch, chuyển sang trạng thái Đang xử lý.");
        context.setState(new ProcessingState());
        return context.getState().handle(context, processor, request);
    }

    @Override
    public String getName() {
        return "Khởi tạo";
    }
}

