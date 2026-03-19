package strategy;

import model.PaymentRequest;
import model.PaymentResult;

import java.math.BigDecimal;

public class CreditCardPaymentStrategy implements PaymentStrategy {
    @Override
    public PaymentResult pay(PaymentRequest request) {
        System.out.println("Xử lý thanh toán bằng THẺ TÍN DỤNG cho đơn " + request.getOrderId());
        // Giả lập luôn thành công
        return new PaymentResult(
                true,
                "Thanh toán thẻ tín dụng thành công",
                request.getAmount(),
                request.getAmount(),
                "CreditCard"
        );
    }

    @Override
    public String getName() {
        return "CreditCard";
    }
}

