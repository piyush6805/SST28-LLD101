
public class Slot {
    // private final String slotId;
    // private final int floorNumber;
    // private final SlotType slotType;
    // private boolean available;
    // private final Map<Integer, Integer> distanceByEntryGate;

    // public Slot(String slotId, int floorNumber, SlotType slotType) {
    //     if (slotId == null || slotId.trim().isEmpty()) {
    //         throw new IllegalArgumentException("slotId cannot be blank");
    //     }
    //     this.slotId = slotId.trim();
    //     this.floorNumber = floorNumber;
    //     this.slotType = slotType;
    //     this.available = true;
    //     this.distanceByEntryGate = new HashMap<Integer, Integer>();
    // }

    // public String getSlotId() {
    //     return slotId;
    // }

    // public int getFloorNumber() {
    //     return floorNumber;
    // }

    // public SlotType getSlotType() {
    //     return slotType;
    // }

    // public boolean isSlotAvailable() {
    //     return available;
    // }

    // public void markOccupied() {
    //     this.available = false;
    // }

    // public void markAvailable() {
    //     this.available = true;
    // }

    // public void setDistanceFromGate(int gateId, int distance) {
    //     distanceByEntryGate.put(gateId, distance);
    // }

    // public int getDistanceFromGate(int gateId) {
    //     Integer value = distanceByEntryGate.get(gateId);
    //     if (value == null) {
    //         throw new IllegalArgumentException("Distance not configured for gate " + gateId + " on slot " + slotId);
    //     }
    //     return value;
    // }
        private int slotId;
    private SlotType slotType;
    private boolean isAvailable;

    public Slot(int slotId, SlotType slotType) {
        this.slotId = slotId;
        this.slotType = slotType;
        this.isAvailable = true;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void occupy() {
        this.isAvailable = false;
    }

    public void free() {
        this.isAvailable = true;
    }

    public SlotType getSlotType() {
        return slotType;
    }

    public int getSlotId() {
        return slotId;
    }

    @Override
    public String toString() {
        return "Slot{" + slotId + ", " + slotType + "}";
    }
}
