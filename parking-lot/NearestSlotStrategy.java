import java.util.*;

public class NearestSlotStrategy implements SlotAllocationStrategy {
    private Map<Integer, TreeSet<Slot>> gateSlotMap;

    public NearestSlotStrategy(Map<Integer, List<Slot>> gateSlots) {
        gateSlotMap = new HashMap<>();

        for (Map.Entry<Integer, List<Slot>> entry : gateSlots.entrySet()) {
            TreeSet<Slot> set = new TreeSet<>(Comparator.comparingInt(Slot::getSlotId));
            set.addAll(entry.getValue());
            gateSlotMap.put(entry.getKey(), set);
        }
    }

    @Override
    public Slot findSlot(Vehicle vehicle, SlotType requestedType, int gateId) {
        TreeSet<Slot> slots = gateSlotMap.get(gateId);

        for (Slot slot : slots) {
            if (isCompatible(vehicle, slot) &&
                slot.getSlotType().ordinal() >= requestedType.ordinal()) {
                return slot;
            }
        }
        return null;
    }

    private boolean isCompatible(Vehicle vehicle, Slot slot) {
        if (vehicle.getType() == VehicleType.BIKE) return true;
        if (vehicle.getType() == VehicleType.CAR)
            return slot.getSlotType() != SlotType.SMALL;
        return slot.getSlotType() == SlotType.LARGE;
    }

    @Override
    public void removeSlot(Slot slot) {
        for (TreeSet<Slot> set : gateSlotMap.values()) {
            set.remove(slot);
        }
    }

    @Override
    public void addSlot(Slot slot) {
        for (TreeSet<Slot> set : gateSlotMap.values()) {
            set.add(slot);
        }
    }
}
