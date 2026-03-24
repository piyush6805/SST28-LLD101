public enum SlotType {
    SMALL,
    MEDIUM,
    LARGE;

    public boolean canFit(VehicleType vehicleType) {
        if (vehicleType == VehicleType.BIKE) {
            return true;
        }
        if (vehicleType == VehicleType.CAR) {
            return this == MEDIUM || this == LARGE;
        }
        return this == LARGE;
    }

    public static SlotType max(SlotType left, SlotType right) {
        if (left == LARGE || right == LARGE) {
            return LARGE;
        }
        if (left == MEDIUM || right == MEDIUM) {
            return MEDIUM;
        }
        return SMALL;
    }
}
