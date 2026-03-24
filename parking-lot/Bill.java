import java.time.LocalDateTime;

public class Bill {
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
