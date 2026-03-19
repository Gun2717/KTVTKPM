package state;

import decorator.TaxCalculator;

import java.math.BigDecimal;

public class TaxExemptState implements TaxState {
    @Override
    public TaxResult handle(ProductContext context, TaxCalculator calculator) {
        BigDecimal subtotal = context.subtotal();
        BigDecimal tax = BigDecimal.ZERO;
        BigDecimal total = subtotal;

        System.out.println("Miễn thuế cho sản phẩm nhu yếu phẩm.");
        System.out.println("Tạm tính: " + subtotal + " | Thuế: " + tax + " | Tổng: " + total);
        return new TaxResult(subtotal, tax, total, "Miễn thuế");
    }

    @Override
    public String getName() {
        return "Miễn thuế";
    }
}

