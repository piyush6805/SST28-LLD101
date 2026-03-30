import java.util.*; 
public class SearchService {

    private final List<Show> shows;

    public SearchService(List<Show> shows) {
        this.shows = shows;
    }

    public List<Show> getShowsByMovie(Movie movie) {
        return shows.stream()
                .filter(s -> s.getMovie().equals(movie))
                .toList();
    }

    public List<Show> getShowsByTheater(Theater theater) {
        return shows.stream()
                .filter(s -> s.getScreen().getTheater().equals(theater))
                .toList();
    }

    public List<Movie> getMoviesByCity(String city) {
        return shows.stream()
                .filter(s -> s.getScreen().getTheater().getCity().equals(city))
                .map(Show::getMovie)
                .distinct()
                .toList();
    }
}