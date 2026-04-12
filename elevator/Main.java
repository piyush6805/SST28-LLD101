public class Main {

    public static void main(String[] args) {
        System.out.println("Starting Elevator System Demo...\n");

        ElevatorController controller = new ElevatorController(
                new WaitingTimeOptimizationStrategy()
        );

        // Setup elevators
        ElevatorCar e1 = new ElevatorCar("E1", 750);
        ElevatorCar e2 = new ElevatorCar("E2", 750);
        controller.addElevator(e1);
        controller.addElevator(e2);

        // Floor buttons
        FloorPanel floor3 = new FloorPanel(3, controller);
        FloorPanel floor8 = new FloorPanel(8, controller);

        // Inside elevator
        ElevatorPanel panel1 = new ElevatorPanel(e1);

        System.out.println("--- Basic Operations ---\n");
        floor3.pressUp();
        floor8.pressDown();
        panel1.pressFloor(10);

        controller.simulate(8);

        System.out.println("\n--- Weight Safety Test ---\n");
        e1.addLoad(700);
        System.out.println("Trying to close door with 700kg...");
        panel1.closeDoor(); // OK
        e1.addLoad(100);    // Now overloaded
        System.out.println("Trying to close door with 800kg (over 750 limit)...");
        panel1.closeDoor(); // Will fail

        System.out.println("\n--- Emergency Button ---\n");
        panel1.pressEmergency();
        controller.step();

        System.out.println("\nDemo Complete! Check FullDemoWithAllScenarios.java for extended tests.");
    }
}