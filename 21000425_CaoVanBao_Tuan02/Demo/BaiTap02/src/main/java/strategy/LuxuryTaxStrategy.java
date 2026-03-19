package strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LuxuryTaxStrategy implements TaxStrategy {
    private final BigDecimal rate;

    public LuxuryTaxStrategy(BigDecimal rate) {
        this.rate = rate;
    }

    @Override
    public BigDecimal calculateTax(BigDecimal taxableAmount) {
        return taxableAmount.multiply(rate).setScale(0, RoundingMode.HALF_UP);
    }

    @Override
    public String getName() {
        return "Thuế xa xỉ";
    }
}

