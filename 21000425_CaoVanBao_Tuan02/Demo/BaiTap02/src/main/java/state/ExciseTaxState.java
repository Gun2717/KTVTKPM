package state;

import decorator.AddTaxDecorator;
import decorator.BaseTaxCalculator;
import decorator.TaxCalculator;
import strategy.ExciseTaxStrategy;
import strategy.VatTaxStrategy;

import java.math.BigDecimal;

public class ExciseTaxState implements TaxState {
    private static final BigDecimal VAT_RATE = new BigDecimal("0.10");
    private static final BigDecimal EXCISE_RATE = new BigDecimal("0.15");

    @Override
    public TaxResult handle(ProductContext context, TaxCalculator calculator) {
        TaxCalculator configured =
                new AddTaxDecorator(
                        new AddTaxDecorator(new BaseTaxCalculator(), new VatTaxStrategy(VAT_RATE)),
                        new ExciseTaxStrategy(EXCISE_RATE)
                );

        BigDecimal subtotal = context.subtotal();
        BigDecimal tax = configured.tax(subtotal);
        BigDecimal total = subtotal.add(tax);

        System.out.println("Áp dụng: " + configured.description());
        System.out.println("Tạm tính: " + subtotal + " | Thuế: " + tax + " | Tổng: " + total);
        return new TaxResult(subtotal, tax, total, configured.description());
    }

    @Override
    public String getName() {
        return "Thuế tiêu thụ";
    }
}

