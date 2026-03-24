import java.time.LocalDateTime;

interface PricingStrategy {
    double calculateAmount(Ticket ticket, LocalDateTime exitTime);
}