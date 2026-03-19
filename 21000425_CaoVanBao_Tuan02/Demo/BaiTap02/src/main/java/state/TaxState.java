package state;

import decorator.TaxCalculator;

public interface TaxState {
    TaxResult handle(ProductContext context, TaxCalculator calculator);
    String getName();
}

