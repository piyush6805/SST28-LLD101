import java.util.*;
public class Payment {
    private final String paymentId;
    private final double amount;
    private PaymentStatus status;

    public Payment(double amount) {
        this.paymentId = UUID.randomUUID().toString();
        this.amount = amount;
        this.status = PaymentStatus.PENDING;
    }

    public void markSuccess() {
        this.status = PaymentStatus.SUCCESS;
    }

    public void markFailed() {
        this.status = PaymentStatus.FAILED;
    }

    public PaymentStatus getStatus() { return status; }
    public double getAmount() { return amount; }
}