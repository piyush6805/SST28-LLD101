import java.util.*;
public class Show {
    private final String showId;
    private final Movie movie;
    private final Screen screen;
    private final long startTime;
    private final List<ShowSeat> showSeats;

    public Show(String showId, Movie movie, Screen screen,
                long startTime, PricingStrategy pricingStrategy) {
        this.showId = showId;
        this.movie = movie;
        this.screen = screen;
        this.startTime = startTime;
        this.showSeats = createShowSeats(pricingStrategy);
    }

    private List<ShowSeat> createShowSeats(PricingStrategy pricingStrategy) {
        List<ShowSeat> list = new ArrayList<>();
        for (Seat seat : screen.getSeats()) {
            ShowSeat ss = new ShowSeat(seat);
            ss.setPrice(pricingStrategy.calculatePrice(seat, this));
            list.add(ss);
        }
        return list;
    }

    public List<ShowSeat> getShowSeats() { return showSeats; }
    public Movie getMovie() { return movie; }
    public Screen getScreen() { return screen; }
    public long getStartTime() { return startTime; }
}