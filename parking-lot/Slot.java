public class Slot {
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
