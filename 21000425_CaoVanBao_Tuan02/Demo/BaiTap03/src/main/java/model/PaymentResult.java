package model;

import java.math.BigDecimal;

public record PaymentResult(boolean success,
                            String message,
                            BigDecimal originalAmount,
                            BigDecimal finalAmount,
                            String breakdown) {
}

