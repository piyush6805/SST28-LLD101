import java.time.LocalDateTime;

public class ExitGate {
    private final int gateId;

    public ExitGate(int gateId) {
        this.gateId = gateId;
    }

    public int getGateId() {
        return gateId;
    }

    public Bill generateBill(Ticket ticket, double amount, LocalDateTime exitTime) {
        return new Bill(ticket.getTicketId(), amount, exitTime);
    }
}
