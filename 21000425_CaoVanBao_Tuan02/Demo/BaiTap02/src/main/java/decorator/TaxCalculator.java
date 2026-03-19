package decorator;

import java.math.BigDecimal;

public interface TaxCalculator {
    BigDecimal tax(BigDecimal taxableAmount);
    String description();
}

