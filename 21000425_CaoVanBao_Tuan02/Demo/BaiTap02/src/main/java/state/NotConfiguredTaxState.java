package state;

import decorator.BaseTaxCalculator;
import decorator.TaxCalculator;
import model.ProductType;

public class NotConfiguredTaxState implements TaxState {
    @Override
    public TaxResult handle(ProductContext context, TaxCalculator calculator) {
        ProductType type = context.getProduct().getType();
        TaxState next = switch (type) {
            case ESSENTIAL -> new TaxExemptState();
            case STANDARD -> new StandardTaxState();
            case EXCISE -> new ExciseTaxState();
            case LUXURY -> new LuxuryTaxState();
        };
        context.setState(next);
        return next.handle(context, new BaseTaxCalculator());
    }

    @Override
    public String getName() {
        return "Chưa cấu hình thuế";
    }
}

