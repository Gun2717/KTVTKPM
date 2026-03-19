package decorator;

import java.math.BigDecimal;

public abstract class TaxCalculatorDecorator implements TaxCalculator {
    protected final TaxCalculator wrappee;

    protected TaxCalculatorDecorator(TaxCalculator wrappee) {
        this.wrappee = wrappee;
    }

    @Override
    public BigDecimal tax(BigDecimal taxableAmount) {
        return wrappee.tax(taxableAmount);
    }

    @Override
    public String description() {
        return wrappee.description();
    }
}

