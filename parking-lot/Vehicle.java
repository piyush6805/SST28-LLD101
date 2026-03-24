public class Vehicle {
    // private final String vehicleId;
    // private final VehicleType vehicleType;

    // public Vehicle(String vehicleId, VehicleType vehicleType) {
    //     if (vehicleId == null || vehicleId.trim().isEmpty()) {
    //         throw new IllegalArgumentException("vehicleId cannot be blank");
    //     }
    //     if (vehicleType == null) {
    //         throw new IllegalArgumentException("vehicleType cannot be null");
    //     }
    //     this.vehicleId = vehicleId.trim();
    //     this.vehicleType = vehicleType;
    // }

    // public String getVehicleId() {
    //     return vehicleId;
    // }

    // public VehicleType getVehicleType() {
    //     return vehicleType;
    // }
    private String vehicleId;
    private VehicleType type;

    public Vehicle(String vehicleId, VehicleType type) {
        this.vehicleId = vehicleId;
        this.type = type;
    }

    public VehicleType getType() {
        return type;
    }

    public String getVehicleId() {
        return vehicleId;
    }
}
