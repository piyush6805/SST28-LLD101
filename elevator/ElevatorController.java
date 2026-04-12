import java.util.*;

public class ElevatorController {

    private List<ElevatorCar> elevators;
    private SchedulerStrategy strategy;
    private Queue<Request> pendingRequests;

    public ElevatorController(SchedulerStrategy strategy) {
        this.strategy = strategy;
        this.elevators = new ArrayList<>();
        this.pendingRequests = new LinkedList<>();
    }

    public void addElevator(ElevatorCar elevator) {
        if (elevator != null) {
            elevators.add(elevator);
            System.out.println("Elevator " + elevator.getId() + " added to system");
        }
    }

    public void removeElevator(String elevatorId) {
        elevators.removeIf(e -> e.getId().equals(elevatorId));
        System.out.println("Elevator " + elevatorId + " removed from system");
    }

    public void handleRequest(Request request) {
        if (request == null) return;

        ElevatorCar selected = strategy.selectElevator(elevators, request);

        if (selected == null) {
            System.out.println("[REQUEST QUEUED] No elevator available for floor " + request.getFloor() + ", queuing request");
            pendingRequests.offer(request);
            return;
        }

        assignRequest(selected, request);
    }

    private void assignRequest(ElevatorCar elevator, Request request) {
        elevator.addRequest(request.getFloor());
        System.out.println("[ASSIGNED] Elevator " + elevator.getId() + " assigned to floor " + request.getFloor());
    }

    public void setElevatorMaintenance(String elevatorId, boolean maintenance) {
        for (ElevatorCar e : elevators) {
            if (e.getId().equals(elevatorId)) {
                e.setUnderMaintenance(maintenance);
                return;
            }
        }
        System.out.println("Elevator " + elevatorId + " not found");
    }

    public List<ElevatorCar> getAvailableElevators() {
        List<ElevatorCar> available = new ArrayList<>();
        for (ElevatorCar e : elevators) {
            if (!e.isUnderMaintenance()) {
                available.add(e);
            }
        }
        return available;
    }

    public void step() {
        for (ElevatorCar e : elevators) {
            e.move();
        }

        if (!pendingRequests.isEmpty() && !getAvailableElevators().isEmpty()) {
            Request pending = pendingRequests.poll();
            handleRequest(pending);
        }
    }

    public void simulate(int steps) {
        for (int i = 0; i < steps; i++) {
            step();
        }
    }

    public void printStatus() {
        for (ElevatorCar e : elevators) {
            System.out.printf("%s: Floor %d | %s | Dir: %s | Load: %d/%d kg | Queue: %d%n",
                    e.getId(),
                    e.getCurrentFloor(),
                    e.getState(),
                    e.getDirection(),
                    e.getCurrentLoad(),
                    e.getCapacity(),
                    e.getQueueSize());
        }
    }

    public List<ElevatorCar> getAllElevators() {
        return new ArrayList<>(elevators);
    }
}