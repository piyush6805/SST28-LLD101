import java.util.*;

/**
 * Waiting time optimization strategy.
 * Prioritizes minimizing user wait time by:
 * 1. Selecting the closest idle elevator
 * 2. If no idle, selecting the elevator with the smallest queue
 * 3. Avoiding elevators under maintenance
 */
public class WaitingTimeOptimizationStrategy implements SchedulerStrategy {

    @Override
    public ElevatorCar selectElevator(List<ElevatorCar> elevators, Request request) {
        ElevatorCar best = null;
        int minWaitScore = Integer.MAX_VALUE;

        for (ElevatorCar e : elevators) {
            // Skip elevators under maintenance
            if (e.isUnderMaintenance()) continue;

            // Idle elevators are best
            if (e.getState() == ElevatorState.IDLE) {
                int distance = Math.abs(e.getCurrentFloor() - request.getFloor());
                if (distance < minWaitScore) {
                    minWaitScore = distance;
                    best = e;
                }
            } else {
                // For moving elevators, consider queue size and distance
                int waitScore = e.getQueueSize() * 100 + Math.abs(e.getCurrentFloor() - request.getFloor());
                if (waitScore < minWaitScore) {
                    minWaitScore = waitScore;
                    best = e;
                }
            }
        }

        return best;
    }
}
