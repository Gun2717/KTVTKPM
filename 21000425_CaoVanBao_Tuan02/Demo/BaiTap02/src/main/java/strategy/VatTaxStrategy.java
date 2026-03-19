package strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class VatTaxStrategy implements TaxStrategy {
    private final BigDecimal rate; // e.g. 0.10 for 10%

    public VatTaxStrategy(BigDecimal rate) {
        this.rate = rate;
    }

    @Override
    public BigDecimal calculateTax(BigDecimal taxableAmount) {
        return taxableAmount.multiply(rate).setScale(0, RoundingMode.HALF_UP);
    }

    @Override
    public String getName() {
        return "VAT";
    }
}

