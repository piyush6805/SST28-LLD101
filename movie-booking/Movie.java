public class Movie {
    private final String movieId;
    private final String name;
    private final int duration;

    public Movie(String movieId, String name, int duration) {
        this.movieId = movieId;
        this.name = name;
        this.duration = duration;
    }

    public String getMovieId() { return movieId; }
}