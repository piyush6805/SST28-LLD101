// import java.time.LocalDateTime;

// public class Main {
//     public static void main(String[] args) {
//         ParkingLot lot = ParkingLotFactory.createDefaultLot();

//         LocalDateTime t0 = LocalDateTime.now();
//         Ticket bikeTicket = lot.park(new Vehicle("BIKE-101", VehicleType.BIKE), t0, SlotType.SMALL, 1);
//         Ticket carTicket = lot.park(new Vehicle("CAR-201", VehicleType.CAR), t0.plusMinutes(10), SlotType.MEDIUM, 2);

//         System.out.println("Status after parking: " + lot.status());
//         System.out.println("Bike ticket: " + bikeTicket.getTicketId() + " -> " + bikeTicket.getSlot().getSlotId());
//         System.out.println("Car ticket: " + carTicket.getTicketId() + " -> " + carTicket.getSlot().getSlotId());

//         Bill bikeBill = lot.exit(bikeTicket, t0.plusHours(2));
//         System.out.println("Bike bill: " + bikeBill.getAmount());
//         System.out.println("Status after exit: " + lot.status());
//     }
// }
import java.time.LocalDateTime;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        List<Slot> slots = new ArrayList<>();

        for (int i = 1; i <= 5; i++) slots.add(new Slot(i, SlotType.SMALL));
        for (int i = 6; i <= 10; i++) slots.add(new Slot(i, SlotType.MEDIUM));
        for (int i = 11; i <= 15; i++) slots.add(new Slot(i, SlotType.LARGE));

        Floor floor = new Floor(1, slots);

        EntryGate entryGate = new EntryGate(1);
        ExitGate exitGate = new ExitGate(1);

        Map<Integer, List<Slot>> gateSlots = new HashMap<>();
        gateSlots.put(1, slots);

        SlotAllocationStrategy slotStrategy = new NearestSlotStrategy(gateSlots);
        PricingStrategy pricingStrategy = new HourlyPricingStrategy();

        ParkingLot parkingLot = new ParkingLot(
                List.of(floor),
                List.of(entryGate),
                List.of(exitGate),
                slotStrategy,
                pricingStrategy
        );

        Vehicle bike = new Vehicle("B1", VehicleType.BIKE);
        Vehicle car = new Vehicle("C1", VehicleType.CAR);

        Ticket t1 = parkingLot.park(bike, LocalDateTime.now(), SlotType.SMALL, 1);
        System.out.println("Parked: " + t1);

        Ticket t2 = parkingLot.park(car, LocalDateTime.now(), SlotType.MEDIUM, 1);
        System.out.println("Parked: " + t2);

        System.out.println("Available Slots: " + parkingLot.status());
        Bill bill = parkingLot.unpark(t1, 1);
        System.out.println("Unparked Bike → " + bill);

        System.out.println("Available Slots: " + parkingLot.status());
    }
}