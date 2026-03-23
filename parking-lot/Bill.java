import java.time.LocalDateTime;

public class Bill {
    private final String ticketId;
    private final double amount;
    private final LocalDateTime exitTime;

    public Bill(String ticketId, double amount, LocalDateTime exitTime) {
        this.ticketId = ticketId;
        this.amount = amount;
        this.exitTime = exitTime;
    }

    public String getTicketId() {
        return ticketId;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }
}
