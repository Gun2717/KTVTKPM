package decorator;

import model.PaymentRequest;
import model.PaymentResult;

import java.math.BigDecimal;

public class DiscountDecorator extends PaymentProcessorDecorator {
    private final BigDecimal discountRate; // 0.10 = 10%

    public DiscountDecorator(PaymentProcessor wrappee, BigDecimal discountRate) {
        super(wrappee);
        this.discountRate = discountRate;
    }

    @Override
    public PaymentResult process(PaymentRequest request) {
        PaymentResult baseResult = super.process(request);
        if (!baseResult.success()) {
        return baseResult;
        }
        BigDecimal discount = baseResult.finalAmount().multiply(discountRate);
        BigDecimal newFinal = baseResult.finalAmount().subtract(discount);
        String breakdown = baseResult.breakdown() + " - Discount(" + discountRate + ")";
        System.out.println("Áp dụng mã giảm giá " + discountRate + " cho đơn " + request.getOrderId()
                + " -> giảm: " + discount);
        return new PaymentResult(true, baseResult.message(), baseResult.originalAmount(), newFinal, breakdown);
    }

    @Override
    public String description() {
        return wrappee.description() + " - Discount(" + discountRate + ")";
    }
}

