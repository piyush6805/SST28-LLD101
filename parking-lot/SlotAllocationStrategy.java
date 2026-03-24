interface SlotAllocationStrategy {
    Slot findSlot(Vehicle vehicle, SlotType requestedType, int gateId);
    void removeSlot(Slot slot);
    void addSlot(Slot slot);
}
