import java.time.LocalDateTime;
import java.util.UUID;

public class EntryGate {
    // private final int gateId;

    // public EntryGate(int gateId) {
    //     this.gateId = gateId;
    // }

    // public int getGateId() {
    //     return gateId;
    // }

    // public Ticket generateTicket(Vehicle vehicle, Slot slot, LocalDateTime entryTime) {
    //     return new Ticket(entryTime, slot, vehicle, gateId);
    // }
    private int gateId;

    public EntryGate(int gateId) {
        this.gateId = gateId;
    }

    public int getGateId() {
        return gateId;
    }

    public Ticket generateTicket(Vehicle vehicle, Slot slot, LocalDateTime entryTime) {
        return new Ticket(UUID.randomUUID().toString(), vehicle, slot, entryTime);
    }
}
