package decorator;

import java.math.BigDecimal;

public class BaseTaxCalculator implements TaxCalculator {
    @Override
    public BigDecimal tax(BigDecimal taxableAmount) {
        return BigDecimal.ZERO;
    }

    @Override
    public String description() {
        return "Không thuế";
    }
}

