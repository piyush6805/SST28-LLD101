import java.util.*;

public class HostelFeeCalculator {
    private static final Money DEPOSIT = new Money(5000.00);
    private final FeeRegistry registry;

    public HostelFeeCalculator(FeeRegistry registry) {
        this.registry = registry;
    }

    public FeeResult calculate(BookingRequest req) {
        List<FeeComponent> components = registry.resolve(req);

        Money monthly = Money.ZERO;
        for (FeeComponent component : components) {
            monthly = monthly.plus(component.monthlyFee());
        }

        return new FeeResult(monthly, DEPOSIT);
    }
}
