// public interface SlotAllocationStrategy {
//     Slot findSlot(Vehicle vehicle, int gateId, SlotType requestedSlotType);

//     void onSlotOccupied(Slot slot);

//     void onSlotFreed(Slot slot);
// }
interface SlotAllocationStrategy {
    Slot findSlot(Vehicle vehicle, SlotType requestedType, int gateId);
    void removeSlot(Slot slot);
    void addSlot(Slot slot);
}
