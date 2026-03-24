public class Vehicle {
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
