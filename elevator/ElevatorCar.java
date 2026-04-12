import java.util.*;

public class ElevatorCar {

    private String id;
    private int currentFloor;
    private Direction direction;
    private ElevatorState state;
    private int capacity;
    private int currentLoad;
    private boolean doorOpen;
    private TreeSet<Integer> requests;
    private int emergencyFloor;
    private boolean underMaintenance;

    public ElevatorCar(String id, int capacity) {
        this.id = id;
        this.capacity = capacity;
        this.currentFloor = 0;
        this.direction = Direction.IDLE;
        this.state = ElevatorState.IDLE;
        this.doorOpen = false;
        this.requests = new TreeSet<>();
        this.underMaintenance = false;
        this.emergencyFloor = -1;
    }

    public String getId() {
        return id;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public ElevatorState getState() {
        return state;
    }

    public void setState(ElevatorState state) {
        this.state = state;
    }

    public void addRequest(int floor) {
        requests.add(floor);
    }

    public void addLoad(int weight) {
        currentLoad += weight;
    }

    public void removeLoad(int weight) {
        currentLoad -= weight;
    }

    public void openDoor() {
        doorOpen = true;
        System.out.println(id + " door opened at floor " + currentFloor);
    }

    public void closeDoor() {
        if (currentLoad > capacity) {
            triggerWeightAlarm();
            System.out.println(id + " overloaded (" + currentLoad + "kg > " + capacity + "kg), cannot close door");
            return;
        }
        doorOpen = false;
        System.out.println(id + " door closed at floor " + currentFloor);
    }

    public boolean isDoorOpen() {
        return doorOpen;
    }

    public int getCurrentLoad() {
        return currentLoad;
    }

    public int getCapacity() {
        return capacity;
    }

    public void triggerWeightAlarm() {
        System.out.println("[WEIGHT ALARM] " + id + " exceeds capacity");
    }

    public void triggerEmergencyAlarm() {
        System.out.println("[EMERGENCY ALARM] " + id + " - stopping at nearest floor");
        state = ElevatorState.EMERGENCY;
        emergencyFloor = currentFloor;
    }

    public void setUnderMaintenance(boolean maintenance) {
        this.underMaintenance = maintenance;
        if (maintenance) {
            state = ElevatorState.MAINTENANCE;
            System.out.println(id + " is now under maintenance");
        } else {
            state = ElevatorState.IDLE;
            System.out.println(id + " maintenance completed");
        }
    }

    public boolean isUnderMaintenance() {
        return underMaintenance;
    }

    public void move() {
        // Don't move if under maintenance or emergency
        if (state == ElevatorState.MAINTENANCE || state == ElevatorState.EMERGENCY) {
            return;
        }

        if (doorOpen) {
            return;
        }

        // If no requests, become idle
        if (requests.isEmpty()) {
            direction = Direction.IDLE;
            state = ElevatorState.IDLE;
            return;
        }

        state = ElevatorState.MOVING;
        int target = requests.first();

        // Move towards the target floor
        if (target > currentFloor) {
            direction = Direction.UP;
            currentFloor++;
            System.out.println(id + " moving UP to floor " + currentFloor);
        } else if (target < currentFloor) {
            direction = Direction.DOWN;
            currentFloor--;
            System.out.println(id + " moving DOWN to floor " + currentFloor);
        }

        // Check if we reached the target floor
        if (currentFloor == target) {
            openDoor();
            requests.remove(target);
        }
    }

    public int getQueueSize() {
        return requests.size();
    }

    public List<Integer> getRequestQueue() {
        return new ArrayList<>(requests);
    }
}