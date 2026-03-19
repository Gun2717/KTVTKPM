package state;

import decorator.TaxCalculator;
import model.Product;

import java.math.BigDecimal;

public class ProductContext {
    private final Product product;
    private TaxState state;

    public ProductContext(Product product) {
        this.product = product;
        this.state = new NotConfiguredTaxState();
    }

    public Product getProduct() {
        return product;
    }

    public TaxState getState() {
        return state;
    }

    public void setState(TaxState state) {
        this.state = state;
    }

    public TaxResult calculate(TaxCalculator calculator) {
        System.out.println("== Sản phẩm: " + product.getName() + " | Loại: " + product.getType() + " | Trạng thái: " + state.getName());
        return state.handle(this, calculator);
    }

    BigDecimal subtotal() {
        return product.subtotal();
    }
}

