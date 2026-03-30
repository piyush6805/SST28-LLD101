import java.util.*;
public class Ticket {
    private final String ticketId;
    private final Booking booking;

    public Ticket(Booking booking) {
        this.ticketId = UUID.randomUUID().toString();
        this.booking = booking;
    }

    public String getTicketId() { return ticketId; }
}