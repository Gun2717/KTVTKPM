import decorator.BaseTaxCalculator;
import model.Product;
import model.ProductType;
import state.ProductContext;
import state.TaxResult;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Product> products = List.of(
                new Product("Gạo 5kg", ProductType.ESSENTIAL, new BigDecimal("150000"), 1),
                new Product("Áo thun", ProductType.STANDARD, new BigDecimal("200000"), 2),
                new Product("Bia lon", ProductType.EXCISE, new BigDecimal("18000"), 24),
                new Product("Đồng hồ cao cấp", ProductType.LUXURY, new BigDecimal("25000000"), 1)
        );

        for (Product p : products) {
            ProductContext ctx = new ProductContext(p);
            TaxResult result = ctx.calculate(new BaseTaxCalculator()); // state sẽ tự cấu hình thuế phù hợp
            System.out.println("Kết quả -> Subtotal: " + result.subtotal()
                    + " | Tax: " + result.tax()
                    + " | Total: " + result.total()
                    + " | (" + result.calculatorDescription() + ")");
            System.out.println();
        }
    }
}

