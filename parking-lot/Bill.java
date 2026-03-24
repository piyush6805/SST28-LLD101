import java.time.LocalDateTime;

public class Bill {
    // private final String ticketId;
    // private final double amount;
    // private final LocalDateTime exitTime;

    // public Bill(String ticketId, double amount, LocalDateTime exitTime) {
    //     this.ticketId = ticketId;
    //     this.amount = amount;
    //     this.exitTime = exitTime;
    // }

    // public String getTicketId() {
    //     return ticketId;
    // }

    // public double getAmount() {
    //     return amount;
    // }

    // public LocalDateTime getExitTime() {
    //     return exitTime;
    // }
    private double amount;
    private LocalDateTime exitTime;

    public Bill(double amount, LocalDateTime exitTime) {
        this.amount = amount;
        this.exitTime = exitTime;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Bill{amount=" + amount + "}";
    }   
}
