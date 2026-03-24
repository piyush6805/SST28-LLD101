import java.time.LocalDateTime;
import java.util.UUID;

public class Ticket {
    private final String ticketId;
    private final LocalDateTime entryTime;
    private final Slot slot;
    private final Vehicle vehicle;
    private final int entryGateId;

    public Ticket(LocalDateTime entryTime, Slot slot, Vehicle vehicle, int entryGateId) {
        if (entryTime == null) {
            throw new IllegalArgumentException("entryTime cannot be null");
        }
        this.ticketId = "T-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.entryTime = entryTime;
        this.slot = slot;
        this.vehicle = vehicle;
        this.entryGateId = entryGateId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public Slot getSlot() {
        return slot;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public int getEntryGateId() {
        return entryGateId;
    }
}
