package model;

import java.math.BigDecimal;
import java.util.Objects;

public class PaymentRequest {
    private final String orderId;
    private final BigDecimal amount;
    private final PaymentMethodType methodType;

    public PaymentRequest(String orderId, BigDecimal amount, PaymentMethodType methodType) {
        this.orderId = Objects.requireNonNull(orderId, "orderId");
        this.amount = Objects.requireNonNull(amount, "amount");
        if (amount.signum() <= 0) throw new IllegalArgumentException("Amount must be > 0");
        this.methodType = Objects.requireNonNull(methodType, "methodType");
    }

    public String getOrderId() {
        return orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PaymentMethodType getMethodType() {
        return methodType;
    }
}

