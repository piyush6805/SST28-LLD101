import java.time.LocalDateTime;

public interface PricingStrategy {
    double calculateAmount(Ticket ticket, LocalDateTime exitTime);
}
