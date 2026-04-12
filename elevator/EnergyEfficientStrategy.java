import java.util.*;

/**
 * Energy-efficient elevator selection strategy.
 * Prioritizes elevators that are:
 * 1. Closest to the request floor (minimize distance)
 * 2. Moving in the same direction as the request
 * 3. Not under maintenance
 */
public class EnergyEfficientStrategy implements SchedulerStrategy {

    @Override
    public ElevatorCar selectElevator(List<ElevatorCar> elevators, Request request) {
        ElevatorCar best = null;
        int minEnergyCost = Integer.MAX_VALUE;

        for (ElevatorCar e : elevators) {
            // Skip elevators under maintenance
            if (e.isUnderMaintenance()) continue;

            // Calculate energy cost
            int distance = Math.abs(e.getCurrentFloor() - request.getFloor());
            int directionCost = isMovingInRightDirection(e, request) ? 0 : 50;
            int loadCost = e.getCurrentLoad();

            int totalCost = distance + directionCost + (loadCost / 100);

            if (totalCost < minEnergyCost) {
                minEnergyCost = totalCost;
                best = e;
            }
        }

        return best;
    }

    private boolean isMovingInRightDirection(ElevatorCar elevator, Request request) {
        Direction elevatorDir = elevator.getDirection();
        Direction requestDir = request.getDirection();

        if (elevatorDir == Direction.IDLE) {
            return true;
        }

        return elevatorDir == requestDir;
    }
}
