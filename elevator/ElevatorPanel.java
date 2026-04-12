/**
 * Control panel inside an elevator - floor buttons, door control, emergency
 */
public class ElevatorPanel {

    private ElevatorCar elevator;

    public ElevatorPanel(ElevatorCar elevator) {
        this.elevator = elevator;
    }

    public void pressFloor(int floor) {
        if (elevator.isUnderMaintenance()) {
            System.out.println("Elevator under maintenance");
            return;
        }
        elevator.addRequest(floor);
    }

    // Door control buttons
    public void openDoor() {
        if (elevator.getState() == ElevatorState.MAINTENANCE) {
            return;
        }
        elevator.openDoor();
    }

    public void closeDoor() {
        if (elevator.getState() == ElevatorState.MAINTENANCE) {
            return;
        }
        elevator.closeDoor();
    }

    // Emergency button - stops at nearest floor and triggers alarm
    public void pressEmergency() {
        elevator.triggerEmergencyAlarm();
        elevator.openDoor();
    }

    // Get elevator status
    public ElevatorState getElevatorState() {
        return elevator.getState();
    }

    public int getCurrentFloor() {
        return elevator.getCurrentFloor();
    }

    public int getCurrentLoad() {
        return elevator.getCurrentLoad();
    }

    public boolean isDoorOpen() {
        return elevator.isDoorOpen();
    }
}