import java.util.*;

/**
 * Basic nearest elevator selection strategy.
 * Selects the elevator closest to the request floor.
 * Avoids elevators under maintenance.
 */
public class NearestElevatorStrategy implements SchedulerStrategy {

    public ElevatorCar selectElevator(List<ElevatorCar> elevators, Request request) {

        ElevatorCar best = null;
        int minDist = Integer.MAX_VALUE;

        for (ElevatorCar e : elevators) {

            // Skip elevators under maintenance
            if (e.isUnderMaintenance()) {
                continue;
            }

            int dist = Math.abs(e.getCurrentFloor() - request.getFloor());

            if (dist < minDist) {
                minDist = dist;
                best = e;
            }
        }

        return best;
    }
}