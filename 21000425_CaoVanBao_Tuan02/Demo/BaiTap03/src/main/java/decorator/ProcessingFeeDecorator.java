package decorator;

import model.PaymentRequest;
import model.PaymentResult;

import java.math.BigDecimal;

public class ProcessingFeeDecorator extends PaymentProcessorDecorator {
    private final BigDecimal feeRate; // e.g. 0.02 for 2%

    public ProcessingFeeDecorator(PaymentProcessor wrappee, BigDecimal feeRate) {
        super(wrappee);
        this.feeRate = feeRate;
    }

    @Override
    public PaymentResult process(PaymentRequest request) {
        PaymentResult baseResult = super.process(request);
        if (!baseResult.success()) {
            return baseResult;
        }
        BigDecimal fee = baseResult.finalAmount().multiply(feeRate);
        BigDecimal newFinal = baseResult.finalAmount().add(fee);
        String breakdown = baseResult.breakdown() + " + Fee(" + feeRate + ")";
        System.out.println("Thêm phí xử lý " + feeRate + " cho đơn " + request.getOrderId()
                + " -> phí: " + fee);
        return new PaymentResult(true, baseResult.message(), baseResult.originalAmount(), newFinal, breakdown);
    }

    @Override
    public String description() {
        return wrappee.description() + " + ProcessingFee(" + feeRate + ")";
    }
}

