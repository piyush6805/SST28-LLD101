import java.time.LocalDateTime;

public class Ticket {
//     private String ticketId;
//     private Vehicle vehicle;
//     private Slot slot;
//     private LocalDateTime entryTime;

//     public Ticket(String ticketId, Vehicle vehicle, Slot slot, LocalDateTime entryTime) {
//         this.ticketId = ticketId;
//         this.vehicle = vehicle;
//         this.slot = slot;
//         this.entryTime = entryTime;
//     }

//     public Slot getSlot() {
//         return slot;
//     }

//     public LocalDateTime getEntryTime() {
//         return entryTime;
//     }

//     public Vehicle getVehicle() {
//         return vehicle;
//     }

//     @Override
//     public String toString() {
//         return "Ticket{" + ticketId + ", slot=" + slot + "}";
//     }
    private String ticketId;
    private Vehicle vehicle;
    private Slot slot;
    private LocalDateTime entryTime;

    public Ticket(String ticketId, Vehicle vehicle, Slot slot, LocalDateTime entryTime) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.slot = slot;
        this.entryTime = entryTime;
    }

    public Slot getSlot() {
        return slot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    @Override
    public String toString() {
        return "Ticket{" + ticketId + ", slot=" + slot + "}";
    }
}
