package state;

import decorator.PaymentProcessor;
import model.PaymentRequest;
import model.PaymentResult;

public class CompletedState implements PaymentState {
    @Override
    public PaymentResult handle(PaymentContext context, PaymentProcessor processor, PaymentRequest request) {
        System.out.println("Giao dịch đã HOÀN TẤT thành công.");
        // Ở đây chỉ log thêm, kết quả đã được tính ở ProcessingState
        return new PaymentResult(true, "Giao dịch hoàn tất", request.getAmount(), request.getAmount(), "Completed");
    }

    @Override
    public String getName() {
        return "Hoàn tất";
    }
}

