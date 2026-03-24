import java.time.Duration;
import java.time.LocalDateTime;

public class HourlyPricingStrategy implements PricingStrategy {
    @Override
    public double calculateAmount(Ticket ticket, LocalDateTime exitTime) {
        long hours = Duration.between(ticket.getEntryTime(), exitTime).toHours() + 1;

        switch (ticket.getSlot().getSlotType()) {
            case SMALL: return hours * 10;
            case MEDIUM: return hours * 20;
            case LARGE: return hours * 30;
        }
        return 0;
    }
}
