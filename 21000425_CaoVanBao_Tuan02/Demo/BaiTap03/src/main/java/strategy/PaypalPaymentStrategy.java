package strategy;

import model.PaymentRequest;
import model.PaymentResult;

public class PaypalPaymentStrategy implements PaymentStrategy {
    @Override
    public PaymentResult pay(PaymentRequest request) {
        System.out.println("Xử lý thanh toán bằng PAYPAL cho đơn " + request.getOrderId());
        return new PaymentResult(
                true,
                "Thanh toán PayPal thành công",
                request.getAmount(),
                request.getAmount(),
                "PayPal"
        );
    }

    @Override
    public String getName() {
        return "PayPal";
    }
}

