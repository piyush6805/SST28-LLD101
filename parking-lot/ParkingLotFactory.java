import java.util.*;

public final class ParkingLotFactory {
    private ParkingLotFactory() {
    }

    public static ParkingLot createDefaultLot() {
        List<EntryGate> entryGates = new ArrayList<EntryGate>();
        entryGates.add(new EntryGate(1));
        entryGates.add(new EntryGate(2));

        List<ExitGate> exitGates = new ArrayList<ExitGate>();
        exitGates.add(new ExitGate(1));

        List<Floor> floors = new ArrayList<Floor>();
        List<Slot> allSlots = new ArrayList<Slot>();

        floors.add(createFloor(1, 6, 4, 2, allSlots));
        floors.add(createFloor(2, 6, 4, 2, allSlots));

        configureDistances(allSlots, entryGates);

        Set<Integer> gateIds = new HashSet<Integer>();
        for (EntryGate gate : entryGates) {
            gateIds.add(gate.getGateId());
        }

        SlotAllocationStrategy slotStrategy = new NearestSlotStrategy(gateIds, allSlots);
        PricingStrategy pricingStrategy = new HourlyPricingStrategy(10.0, 20.0, 40.0);

        return new ParkingLot(floors, entryGates, exitGates, slotStrategy, pricingStrategy);
    }

    private static Floor createFloor(int floorNumber, int small, int medium, int large, List<Slot> allSlots) {
        List<Slot> slots = new ArrayList<Slot>();
        int running = 1;

        for (int i = 0; i < small; i++) {
            Slot slot = new Slot("F" + floorNumber + "-S" + running, floorNumber, SlotType.SMALL);
            slots.add(slot);
            allSlots.add(slot);
            running++;
        }
        for (int i = 0; i < medium; i++) {
            Slot slot = new Slot("F" + floorNumber + "-M" + running, floorNumber, SlotType.MEDIUM);
            slots.add(slot);
            allSlots.add(slot);
            running++;
        }
        for (int i = 0; i < large; i++) {
            Slot slot = new Slot("F" + floorNumber + "-L" + running, floorNumber, SlotType.LARGE);
            slots.add(slot);
            allSlots.add(slot);
            running++;
        }

        return new Floor(floorNumber, slots);
    }

    private static void configureDistances(List<Slot> slots, List<EntryGate> gates) {
        int index = 0;
        for (Slot slot : slots) {
            for (EntryGate gate : gates) {
                int gateId = gate.getGateId();
                int floorPenalty = (slot.getFloorNumber() - 1) * 100;
                int gateSkew = gateId * 3;
                int distance = floorPenalty + gateSkew + index;
                slot.setDistanceFromGate(gateId, distance);
            }
            index++;
        }
    }
}
