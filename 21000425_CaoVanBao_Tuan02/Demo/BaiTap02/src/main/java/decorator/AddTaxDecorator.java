package decorator;

import strategy.TaxStrategy;

import java.math.BigDecimal;

public class AddTaxDecorator extends TaxCalculatorDecorator {
    private final TaxStrategy taxStrategy;

    public AddTaxDecorator(TaxCalculator wrappee, TaxStrategy taxStrategy) {
        super(wrappee);
        this.taxStrategy = taxStrategy;
    }

    @Override
    public BigDecimal tax(BigDecimal taxableAmount) {
        return super.tax(taxableAmount).add(taxStrategy.calculateTax(taxableAmount));
    }

    @Override
    public String description() {
        String base = super.description();
        if ("Không thuế".equals(base)) return taxStrategy.getName();
        return base + " + " + taxStrategy.getName();
    }
}

