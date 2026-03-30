
import java.util.*;

public class BookingService {

    private final SeatLockService seatLockService;

    public BookingService(SeatLockService seatLockService) {
        this.seatLockService = seatLockService;
    }

    public Booking bookTickets(User user,
            Show show,
            List<ShowSeat> seats,
            PaymentService paymentService) {

        boolean locked = seatLockService.lockSeats(user.getUserId(), seats);

        if (!locked) {
            throw new RuntimeException("Seats not available");
        }

        double total = seats.stream().mapToDouble(ShowSeat::getPrice).sum();

        // Create payment entity
        Payment payment = new Payment(total);

        PaymentStatus status = paymentService.processPayment(user, total);

        if (status != PaymentStatus.SUCCESS) {
            payment.markFailed();
            seats.forEach(ShowSeat::unlock);
            throw new RuntimeException("Payment failed");
        }

        payment.markSuccess();

        Booking booking = new Booking(user, show, seats);

        seats.forEach(ShowSeat::book);
        booking.confirm(payment);

        return booking;
    }
}
