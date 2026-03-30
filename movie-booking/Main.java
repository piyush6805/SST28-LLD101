import java.util.*;
public class Main {
    public static void main(String[] args) {

        List<Seat> seats = List.of(
                new Seat("A1", 1, SeatType.GOLD),
                new Seat("A2", 1, SeatType.GOLD),
                new Seat("A3", 1, SeatType.PLATINUM)
        );

        Theater theater = new Theater("T1", "PVR", "Bangalore", new ArrayList<>());
        Screen screen = new Screen("S1", theater, seats);

        Movie movie = new Movie("M1", "Inception", 120);

        PricingStrategy pricing = new DefaultPricingStrategy();
        Show show = new Show("SH1", movie, screen,
                System.currentTimeMillis(), pricing);

        SeatLockService lockService = new SeatLockService();
        BookingService bookingService = new BookingService(lockService);

        User user = new User("U1", "Piyush");

        List<ShowSeat> selectedSeats = show.getShowSeats().subList(0, 2);

        Booking booking = bookingService.bookTickets(
                user, show, selectedSeats, new UPIPaymentService()
        );

        System.out.println("Booking successful!");
    }
}