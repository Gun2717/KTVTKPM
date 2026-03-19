package strategy;

import java.math.BigDecimal;

public interface TaxStrategy {
    BigDecimal calculateTax(BigDecimal taxableAmount);
    String getName();
}

