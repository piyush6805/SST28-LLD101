import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        ParkingLot lot = ParkingLotFactory.createDefaultLot();

        LocalDateTime t0 = LocalDateTime.now();
        Ticket bikeTicket = lot.park(new Vehicle("BIKE-101", VehicleType.BIKE), t0, SlotType.SMALL, 1);
        Ticket carTicket = lot.park(new Vehicle("CAR-201", VehicleType.CAR), t0.plusMinutes(10), SlotType.MEDIUM, 2);

        System.out.println("Status after parking: " + lot.status());
        System.out.println("Bike ticket: " + bikeTicket.getTicketId() + " -> " + bikeTicket.getSlot().getSlotId());
        System.out.println("Car ticket: " + carTicket.getTicketId() + " -> " + carTicket.getSlot().getSlotId());

        Bill bikeBill = lot.exit(bikeTicket, t0.plusHours(2));
        System.out.println("Bike bill: " + bikeBill.getAmount());
        System.out.println("Status after exit: " + lot.status());
    }
}