import java.time.LocalDateTime;

public class ExitGate {
    // private final int gateId;

    // public ExitGate(int gateId) {
    //     this.gateId = gateId;
    // }

    // public int getGateId() {
    //     return gateId;
    // }

    // public Bill generateBill(Ticket ticket, double amount, LocalDateTime exitTime) {
    //     return new Bill(ticket.getTicketId(), amount, exitTime);
    // }
    private int gateId;

    public ExitGate(int gateId) {
        this.gateId = gateId;
    }

    public int getGateId() {
        return gateId;
    }

    public Bill generateBill(Ticket ticket, PricingStrategy pricingStrategy) {
        double amount = pricingStrategy.calculateAmount(ticket, LocalDateTime.now());
        return new Bill(amount, LocalDateTime.now());
    }
}
