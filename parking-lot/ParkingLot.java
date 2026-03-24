
import java.time.LocalDateTime;
import java.util.*;

public class ParkingLot {
    private List<Floor> floors;
    private List<EntryGate> entryGates;
    private List<ExitGate> exitGates;

    private SlotAllocationStrategy slotStrategy;
    private PricingStrategy pricingStrategy;

    public ParkingLot(List<Floor> floors,
            List<EntryGate> entryGates,
            List<ExitGate> exitGates,
            SlotAllocationStrategy slotStrategy,
            PricingStrategy pricingStrategy) {

        this.floors = floors;
        this.entryGates = entryGates;
        this.exitGates = exitGates;
        this.slotStrategy = slotStrategy;
        this.pricingStrategy = pricingStrategy;
    }

    public Ticket park(Vehicle vehicle,
            LocalDateTime entryTime,
            SlotType requestedSlotType,
            int gateId) {

        Slot slot = slotStrategy.findSlot(vehicle, requestedSlotType, gateId);

        if (slot == null) {
            throw new RuntimeException("No compatible slot available");
        }

        slot.occupy();
        slotStrategy.removeSlot(slot);

        EntryGate gate = entryGates.stream()
                .filter(g -> g.getGateId() == gateId)
                .findFirst()
                .orElseThrow();

        return gate.generateTicket(vehicle, slot, entryTime);
    }

    public Bill unpark(Ticket ticket, int exitGateId) {
        Slot slot = ticket.getSlot();
        slot.free();
        slotStrategy.addSlot(slot);

        ExitGate gate = exitGates.stream()
                .filter(g -> g.getGateId() == exitGateId)
                .findFirst()
                .orElseThrow();

        return gate.generateBill(ticket, pricingStrategy);
    }

    public Map<SlotType, Integer> status() {
        Map<SlotType, Integer> count = new HashMap<>();

        for (Floor f : floors) {
            for (Slot s : f.getSlots()) {
                if (s.isAvailable()) {
                    count.put(s.getSlotType(),
                            count.getOrDefault(s.getSlotType(), 0) + 1);
                }
            }
        }

        return count;
    }
}
