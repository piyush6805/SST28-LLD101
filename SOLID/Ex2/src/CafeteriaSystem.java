import java.util.*;

public class CafeteriaSystem {
    private final Map<String, MenuItem> menu = new LinkedHashMap<>();
    private final InvoiceStore store;
    private final Pricing pricing;
    private final RuleFactory ruleFactory;
    private int invoiceSeq = 1000;

    public CafeteriaSystem(InvoiceStore store, Pricing pricing, RuleFactory ruleFactory) {
        this.store = store;
        this.pricing = pricing;
        this.ruleFactory = ruleFactory;
    }

    public void addToMenu(MenuItem i) { menu.put(i.id, i); }

    public void checkout(String customerType, List<OrderLine> lines) {
        String invId = "INV-" + (++invoiceSeq);

        TaxRule taxRule = ruleFactory.getTaxRule(customerType);
        DiscountRule discountRule = ruleFactory.getDiscountRule(customerType);

        double subtotal = pricing.subtotal(lines, menu);
        double taxPct = taxRule.taxPercent();
        double tax = subtotal * (taxPct / 100.0);
        double discount = discountRule.discountAmount(subtotal, lines.size());
        double total = subtotal + tax - discount;

        String printable = InvoiceFormatter.format(invId, lines, menu,
                subtotal, taxPct, tax, discount, total);
        System.out.print(printable);

        store.save(invId, printable);
        System.out.println("Saved invoice: " + invId + " (lines=" + store.countLines(invId) + ")");
    }
}
