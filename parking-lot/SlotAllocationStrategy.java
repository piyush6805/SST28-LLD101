public interface SlotAllocationStrategy {
    Slot findSlot(Vehicle vehicle, int gateId, SlotType requestedSlotType);

    void onSlotOccupied(Slot slot);

    void onSlotFreed(Slot slot);
}
