package state;

import java.math.BigDecimal;

public record TaxResult(BigDecimal subtotal, BigDecimal tax, BigDecimal total, String calculatorDescription) {}

