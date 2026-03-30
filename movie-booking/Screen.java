import java.util.*;
public class Screen {
    private final String screenId;
    private final Theater theater;
    private final List<Seat> seats;

    public Screen(String screenId, Theater theater, List<Seat> seats) {
        this.screenId = screenId;
        this.theater = theater;
        this.seats = seats;
    }

    public Theater getTheater() { return theater; }
    public List<Seat> getSeats() { return seats; }
}