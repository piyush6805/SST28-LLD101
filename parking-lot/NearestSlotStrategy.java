import java.util.*;

public class NearestSlotStrategy implements SlotAllocationStrategy {
    // private final Map<Integer, TreeSet<Slot>> slotMap;

    // public NearestSlotStrategy(Set<Integer> gateIds, List<Slot> allSlots) {
    //     this.slotMap = new HashMap<Integer, TreeSet<Slot>>();

    //     for (Integer gateId : gateIds) {
    //         TreeSet<Slot> sortedByGateDistance = new TreeSet<Slot>(createComparator(gateId));
    //         sortedByGateDistance.addAll(allSlots);
    //         this.slotMap.put(gateId, sortedByGateDistance);
    //     }
    // }

    // @Override
    // public Slot findSlot(Vehicle vehicle, int gateId, SlotType requestedSlotType) {
    //     TreeSet<Slot> gateSlots = slotMap.get(gateId);
    //     if (gateSlots == null) {
    //         throw new IllegalArgumentException("Unknown entry gate id: " + gateId);
    //     }

    //     SlotType minimumRequired = SlotType.max(vehicle.getVehicleType().minimumSlotType(), requestedSlotType);

    //     for (Slot slot : gateSlots) {
    //         if (isCompatible(slot, vehicle.getVehicleType(), minimumRequired)) {
    //             return slot;
    //         }
    //     }

    //     return null;
    // }

    // @Override
    // public void onSlotOccupied(Slot slot) {
    //     for (TreeSet<Slot> slots : slotMap.values()) {
    //         slots.remove(slot);
    //     }
    // }

    // @Override
    // public void onSlotFreed(Slot slot) {
    //     for (TreeSet<Slot> slots : slotMap.values()) {
    //         slots.add(slot);
    //     }
    // }

    // private Comparator<Slot> createComparator(final int gateId) {
    //     return new Comparator<Slot>() {
    //         @Override
    //         public int compare(Slot left, Slot right) {
    //             int byDistance = Integer.compare(left.getDistanceFromGate(gateId), right.getDistanceFromGate(gateId));
    //             if (byDistance != 0) {
    //                 return byDistance;
    //             }
    //             int byFloor = Integer.compare(left.getFloorNumber(), right.getFloorNumber());
    //             if (byFloor != 0) {
    //                 return byFloor;
    //             }
    //             return left.getSlotId().compareTo(right.getSlotId());
    //         }
    //     };
    // }

    // private boolean isCompatible(Slot slot, VehicleType vehicleType, SlotType minimumRequired) {
    //     if (!slot.isSlotAvailable()) {
    //         return false;
    //     }
    //     if (!slot.getSlotType().canFit(vehicleType)) {
    //         return false;
    //     }
    //     return slot.getSlotType().ordinal() >= minimumRequired.ordinal();
    // }
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
