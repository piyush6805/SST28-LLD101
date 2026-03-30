import java.util.*;
public class Booking {
    private final String bookingId;
    private final User user;
    private final Show show;
    private final List<ShowSeat> seats;

    private BookingStatus status;
    private Payment payment;
    private Ticket ticket;

    public Booking(User user, Show show, List<ShowSeat> seats) {
        this.bookingId = UUID.randomUUID().toString();
        this.user = user;
        this.show = show;
        this.seats = seats;
        this.status = BookingStatus.CREATED;
    }

    public void confirm(Payment payment) {
        this.status = BookingStatus.CONFIRMED;
        this.payment = payment;
        this.ticket = new Ticket(this);
    }

    public void cancel() {
        this.status = BookingStatus.CANCELLED;
    }

    public List<ShowSeat> getSeats() { return seats; }
}