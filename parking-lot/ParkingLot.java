import java.time.LocalDateTime;
import java.util.*;

public class ParkingLot {
    private final List<Floor> floors;
    private final List<EntryGate> entryGates;
    private final List<ExitGate> exitGates;
    private final SlotAllocationStrategy slotStrategy;
    private final PricingStrategy pricingStrategy;
    private final Map<String, Ticket> activeTickets;

    public ParkingLot(
            List<Floor> floors,
            List<EntryGate> entryGates,
            List<ExitGate> exitGates,
            SlotAllocationStrategy slotStrategy,
            PricingStrategy pricingStrategy) {
        this.floors = new ArrayList<Floor>(floors);
        this.entryGates = new ArrayList<EntryGate>(entryGates);
        this.exitGates = new ArrayList<ExitGate>(exitGates);
        this.slotStrategy = slotStrategy;
        this.pricingStrategy = pricingStrategy;
        this.activeTickets = new HashMap<String, Ticket>();
    }

    public Ticket park(Vehicle vehicle, LocalDateTime entryTime, SlotType requestedSlotType, int entryGateId) {
        EntryGate entryGate = findEntryGate(entryGateId);
        Slot slot = slotStrategy.findSlot(vehicle, entryGateId, requestedSlotType);

        if (slot == null) {
            throw new IllegalStateException("No compatible slot available for vehicle " + vehicle.getVehicleId());
        }

        slot.markOccupied();
        slotStrategy.onSlotOccupied(slot);

        Ticket ticket = entryGate.generateTicket(vehicle, slot, entryTime);
        activeTickets.put(ticket.getTicketId(), ticket);
        return ticket;
    }

    public Bill unpark(Ticket ticket, LocalDateTime exitTime, int exitGateId) {
        if (ticket == null) {
            throw new IllegalArgumentException("ticket cannot be null");
        }

        ExitGate exitGate = findExitGate(exitGateId);
        Ticket active = activeTickets.remove(ticket.getTicketId());
        if (active == null) {
            throw new IllegalArgumentException("Ticket not found or already used: " + ticket.getTicketId());
        }

        double amount = pricingStrategy.calculateAmount(active, exitTime);

        Slot slot = active.getSlot();
        slot.markAvailable();
        slotStrategy.onSlotFreed(slot);

        return exitGate.generateBill(active, amount, exitTime);
    }

    public Map<SlotType, Integer> getAvailability() {
        Map<SlotType, Integer> availability = new HashMap<SlotType, Integer>();
        availability.put(SlotType.SMALL, 0);
        availability.put(SlotType.MEDIUM, 0);
        availability.put(SlotType.LARGE, 0);

        for (Floor floor : floors) {
            for (Slot slot : floor.getSlots()) {
                if (!slot.isSlotAvailable()) {
                    continue;
                }
                SlotType slotType = slot.getSlotType();
                availability.put(slotType, availability.get(slotType) + 1);
            }
        }

        return availability;
    }

    public Map<SlotType, Integer> status() {
        return getAvailability();
    }

    public Bill exit(Ticket ticket, LocalDateTime exitTime) {
        ExitGate defaultExit = exitGates.get(0);
        return unpark(ticket, exitTime, defaultExit.getGateId());
    }

    private EntryGate findEntryGate(int gateId) {
        for (EntryGate gate : entryGates) {
            if (gate.getGateId() == gateId) {
                return gate;
            }
        }
        throw new IllegalArgumentException("Entry gate not found: " + gateId);
    }

    private ExitGate findExitGate(int gateId) {
        for (ExitGate gate : exitGates) {
            if (gate.getGateId() == gateId) {
                return gate;
            }
        }
        throw new IllegalArgumentException("Exit gate not found: " + gateId);
    }
}
