package model;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private final String name;
    private final ProductType type;
    private final BigDecimal unitPrice;
    private final int quantity;

    public Product(String name, ProductType type, BigDecimal unitPrice, int quantity) {
        this.name = Objects.requireNonNull(name, "name");
        this.type = Objects.requireNonNull(type, "type");
        this.unitPrice = Objects.requireNonNull(unitPrice, "unitPrice");
        if (unitPrice.signum() < 0) throw new IllegalArgumentException("unitPrice must be >= 0");
        if (quantity <= 0) throw new IllegalArgumentException("quantity must be > 0");
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public ProductType getType() {
        return type;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal subtotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}

