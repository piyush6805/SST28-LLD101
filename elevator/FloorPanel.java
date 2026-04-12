/**
 * Floor call panel with UP and DOWN buttons
 */
public class FloorPanel {

    private int floor;
    private ElevatorController controller;
    private boolean upButtonDisabled;
    private boolean downButtonDisabled;

    public FloorPanel(int floor, ElevatorController controller) {
        this.floor = floor;
        this.controller = controller;
        this.upButtonDisabled = false;
        this.downButtonDisabled = false;
    }

    public void pressUp() {
        if (upButtonDisabled) {
            System.out.println("Floor " + floor + " UP button disabled");
            return;
        }
        controller.handleRequest(new Request(floor, Direction.UP));
    }

    public void pressDown() {
        if (downButtonDisabled) {
            System.out.println("Floor " + floor + " DOWN button disabled");
            return;
        }
        controller.handleRequest(new Request(floor, Direction.DOWN));
    }

    // Maintenance control for floor
    public void setUnderMaintenance(boolean maintenance) {
        this.upButtonDisabled = maintenance;
        this.downButtonDisabled = maintenance;
        if (maintenance) {
            System.out.println("Floor " + floor + " is under maintenance - buttons disabled");
        } else {
            System.out.println("Floor " + floor + " maintenance completed - buttons enabled");
        }
    }

    public boolean isUnderMaintenance() {
        return upButtonDisabled || downButtonDisabled;
    }

    public int getFloor() {
        return floor;
    }
}