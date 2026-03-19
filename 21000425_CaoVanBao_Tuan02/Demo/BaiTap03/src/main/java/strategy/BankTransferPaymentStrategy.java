package strategy;

import model.PaymentRequest;
import model.PaymentResult;

public class BankTransferPaymentStrategy implements PaymentStrategy {
    @Override
    public PaymentResult pay(PaymentRequest request) {
        System.out.println("Xử lý thanh toán bằng CHUYỂN KHOẢN cho đơn " + request.getOrderId());
        return new PaymentResult(
                true,
                "Thanh toán chuyển khoản thành công",
                request.getAmount(),
                request.getAmount(),
                "BankTransfer"
        );
    }

    @Override
    public String getName() {
        return "BankTransfer";
    }
}

