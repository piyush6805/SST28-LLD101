import java.util.*;

public class RuleFactory {
    private final Map<String, TaxRule> taxRules = new HashMap<>();
    private final Map<String, DiscountRule> discountRules = new HashMap<>();
    private final TaxRule defaultTax = new DefaultTaxRule();
    private final DiscountRule defaultDiscount = new DefaultDiscountRule();

    public void registerTaxRule(String customerType, TaxRule rule) {
        taxRules.put(customerType.toLowerCase(), rule);
    }

    public void registerDiscountRule(String customerType, DiscountRule rule) {
        discountRules.put(customerType.toLowerCase(), rule);
    }

    public TaxRule getTaxRule(String customerType) {
        return taxRules.getOrDefault(customerType.toLowerCase(), defaultTax);
    }

    public DiscountRule getDiscountRule(String customerType) {
        return discountRules.getOrDefault(customerType.toLowerCase(), defaultDiscount);
    }
}
