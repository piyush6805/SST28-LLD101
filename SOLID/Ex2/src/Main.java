
import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== Cafeteria Billing ===");

        // Register rules for each customer type
        RuleFactory ruleFactory = new RuleFactory();
        ruleFactory.registerTaxRule("student", new StudentTaxRule());
        ruleFactory.registerTaxRule("staff", new StaffTaxRule());
        ruleFactory.registerDiscountRule("student", new StudentDiscountRule());
        ruleFactory.registerDiscountRule("staff", new StaffDiscountRule());

        CafeteriaSystem sys = new CafeteriaSystem(new FileStore(), new Pricing(), ruleFactory);
        sys.addToMenu(new MenuItem("M1", "Veg Thali", 80.00));
        sys.addToMenu(new MenuItem("C1", "Coffee", 30.00));
        sys.addToMenu(new MenuItem("S1", "Sandwich", 60.00));

        List<OrderLine> order = List.of(
                new OrderLine("M1", 2),
                new OrderLine("C1", 1)
        );

        sys.checkout("student", order);
    }
}
