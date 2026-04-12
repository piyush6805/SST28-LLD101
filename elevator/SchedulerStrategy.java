import java.util.*;

/**
 * Strategy interface for elevator selection
 */
public interface SchedulerStrategy {
    /**
     * Selects the most appropriate elevator for a request.
     *
     * @param elevators Available elevators in the system
     * @param request   The elevator request (floor and direction)
     * @return The selected elevator, or null if no elevator is available
     */
    ElevatorCar selectElevator(List<ElevatorCar> elevators, Request request);
}