import java.util.List;

public class Floor {
    // private final int floorNumber;
    // private final List<Slot> slots;

    // public Floor(int floorNumber, List<Slot> slots) {
    //     this.floorNumber = floorNumber;
    //     this.slots = new ArrayList<Slot>(slots);
    // }

    // public int getFloorNumber() {
    //     return floorNumber;
    // }

    // public List<Slot> getSlots() {
    //     return slots;
    // }
    private int floorNumber;
    private List<Slot> slots;

    public Floor(int floorNumber, List<Slot> slots) {
        this.floorNumber = floorNumber;
        this.slots = slots;
    }

    public List<Slot> getSlots() {
        return slots;
    }
}
