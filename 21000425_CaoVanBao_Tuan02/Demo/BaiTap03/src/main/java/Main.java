import decorator.BasePaymentProcessor;
import decorator.DiscountDecorator;
import decorator.PaymentProcessor;
import decorator.ProcessingFeeDecorator;
import model.PaymentMethodType;
import model.PaymentRequest;
import model.PaymentResult;
import state.PaymentContext;
import strategy.BankTransferPaymentStrategy;
import strategy.CreditCardPaymentStrategy;
import strategy.PaypalPaymentStrategy;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        PaymentContext context = new PaymentContext();

        // Thanh toán 1: Thẻ tín dụng + phí xử lý 2%
        PaymentRequest creditCardRequest = new PaymentRequest("DH1001", new BigDecimal("1000000"), PaymentMethodType.CREDIT_CARD);
        PaymentProcessor ccProcessor =
                new ProcessingFeeDecorator(
                        new BasePaymentProcessor(new CreditCardPaymentStrategy()),
                        new BigDecimal("0.02")
                );
        runScenario(context, ccProcessor, creditCardRequest);

        System.out.println();

        // Thanh toán 2: PayPal + phí xử lý 3% + giảm giá 10%
        PaymentRequest paypalRequest = new PaymentRequest("DH1002", new BigDecimal("500000"), PaymentMethodType.PAYPAL);
        PaymentProcessor paypalProcessor =
                new DiscountDecorator(
                        new ProcessingFeeDecorator(
                                new BasePaymentProcessor(new PaypalPaymentStrategy()),
                                new BigDecimal("0.03")
                        ),
                        new BigDecimal("0.10")
                );
        runScenario(new PaymentContext(), paypalProcessor, paypalRequest);

        System.out.println();

        // Thanh toán 3: Chuyển khoản, không phí, có mã giảm giá 5%
        PaymentRequest bankRequest = new PaymentRequest("DH1003", new BigDecimal("2000000"), PaymentMethodType.BANK_TRANSFER);
        PaymentProcessor bankProcessor =
                new DiscountDecorator(
                        new BasePaymentProcessor(new BankTransferPaymentStrategy()),
                        new BigDecimal("0.05")
                );
        runScenario(new PaymentContext(), bankProcessor, bankRequest);
    }

    private static void runScenario(PaymentContext context, PaymentProcessor processor, PaymentRequest request) {
        System.out.println("=== Mô phỏng thanh toán cho đơn " + request.getOrderId() + " ===");
        PaymentResult result = context.process(processor, request);
        System.out.println("Kết quả: " + (result.success() ? "THÀNH CÔNG" : "THẤT BẠI"));
        System.out.println("Số tiền gốc: " + result.originalAmount()
                + " | Số tiền cuối: " + result.finalAmount()
                + " | Chi tiết: " + result.breakdown());
    }
}

