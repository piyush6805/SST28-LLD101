public class FullDemoWithAllScenarios {

    public static void main(String[] args) {
        System.out.println("Full Elevator System Demo (All Scenarios)\n");

        ElevatorController controller = new ElevatorController(
                new WaitingTimeOptimizationStrategy()
        );

        ElevatorCar e1 = new ElevatorCar("E1", 750);
        ElevatorCar e2 = new ElevatorCar("E2", 750);
        ElevatorCar e3 = new ElevatorCar("E3", 750);

        controller.addElevator(e1);
        controller.addElevator(e2);
        controller.addElevator(e3);

        FloorPanel floor1 = new FloorPanel(1, controller);
        FloorPanel floor5 = new FloorPanel(5, controller);
        FloorPanel floor10 = new FloorPanel(10, controller);

        // Scenario 1: Basic movement
        System.out.println("\n=== Scenario 1: Basic Movement ===\n");
        floor5.pressUp();
        floor10.pressDown();

        ElevatorPanel panel1 = new ElevatorPanel(e1);
        ElevatorPanel panel3 = new ElevatorPanel(e3);
        panel1.pressFloor(8);
        panel3.pressFloor(15);

        controller.simulate(5);

        // Scenario 2: Weight limits
        System.out.println("\n=== Scenario 2: Weight Safety ===\n");
        System.out.println("Adding 700kg to E1...");
        e1.addLoad(700);
        panel1.closeDoor();
        e1.addLoad(100);
        System.out.println("Attempting close with 800kg (limit 750)...");
        panel1.closeDoor();

        // Scenario 3: Maintenance
        System.out.println("\n=== Scenario 3: Elevator Maintenance ===\n");
        System.out.println("Taking E2 offline...");
        controller.setElevatorMaintenance("E2", true);
        floor1.pressUp();
        controller.step();
        controller.printStatus();

        // Scenario 4: Floor maintenance
        System.out.println("\n=== Scenario 4: Floor Maintenance ===\n");
        System.out.println("Floor 10 going down for maintenance...");
        floor10.setUnderMaintenance(true);
        floor10.pressUp();
        floor10.pressDown();

        // Scenario 5: Emergency stop
        System.out.println("\n=== Scenario 5: Emergency Stop ===\n");
        controller.setElevatorMaintenance("E2", false);
        System.out.println("Emergency button pressed...");
        panel1.pressEmergency();
        controller.step();
        controller.printStatus();

        // Scenario 6: Request queuing
        System.out.println("\n=== Scenario 6: Request Queuing ===\n");
        System.out.println("All elevators offline...");
        controller.setElevatorMaintenance("E1", true);
        controller.setElevatorMaintenance("E2", true);
        controller.setElevatorMaintenance("E3", true);

        System.out.println("Making requests while all busy...");
        floor5.pressDown();
        floor1.pressUp();

        System.out.println("Bringing E2 back online...");
        controller.setElevatorMaintenance("E2", false);
        controller.simulate(3);

        // Scenario 7: Different strategy
        System.out.println("\n=== Scenario 7: Energy Efficient Strategy ===\n");
        ElevatorController controller2 = new ElevatorController(
                new EnergyEfficientStrategy()
        );

        ElevatorCar e4 = new ElevatorCar("E4", 800);
        ElevatorCar e5 = new ElevatorCar("E5", 800);
        controller2.addElevator(e4);
        controller2.addElevator(e5);

        FloorPanel floor3 = new FloorPanel(3, controller2);
        FloorPanel floor7 = new FloorPanel(7, controller2);

        System.out.println("Using EnergyEfficientStrategy...\n");
        floor3.pressUp();
        floor7.pressDown();
        controller2.simulate(6);

        System.out.println("\nAll scenarios complete!");
    }
}
