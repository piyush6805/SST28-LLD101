import java.util.*;

public class FeeRegistry {
    private final Map<Integer, FeeComponent> roomFees = new HashMap<>();
    private final Map<AddOn, FeeComponent> addOnFees = new HashMap<>();

    public void registerRoom(int roomType, FeeComponent fee) {
        roomFees.put(roomType, fee);
    }

    public void registerAddOn(AddOn addOn, FeeComponent fee) {
        addOnFees.put(addOn, fee);
    }

    public List<FeeComponent> resolve(BookingRequest req) {
        List<FeeComponent> components = new ArrayList<>();

        FeeComponent room = roomFees.get(req.roomType);
        if (room != null) {
            components.add(room);
        }

        for (AddOn addOn : req.addOns) {
            FeeComponent addOnFee = addOnFees.get(addOn);
            if (addOnFee != null) {
                components.add(addOnFee);
            }
        }

        return components;
    }

    public static FeeRegistry createDefault() {
        FeeRegistry registry = new FeeRegistry();
        registry.registerRoom(LegacyRoomTypes.SINGLE, new SingleRoomFee());
        registry.registerRoom(LegacyRoomTypes.DOUBLE, new DoubleRoomFee());
        registry.registerRoom(LegacyRoomTypes.TRIPLE, new TripleRoomFee());
        registry.registerRoom(LegacyRoomTypes.DELUXE, new DeluxeRoomFee());
        registry.registerAddOn(AddOn.MESS, new MessFee());
        registry.registerAddOn(AddOn.LAUNDRY, new LaundryFee());
        registry.registerAddOn(AddOn.GYM, new GymFee());
        return registry;
    }
}
