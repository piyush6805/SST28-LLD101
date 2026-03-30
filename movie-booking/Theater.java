import java.util.*;
public class Theater {
    private final String theaterId;
    private final String name;
    private final String city;
    private final List<Screen> screens;

    public Theater(String theaterId, String name, String city, List<Screen> screens) {
        this.theaterId = theaterId;
        this.name = name;
        this.city = city;
        this.screens = screens;
    }

    public String getCity() { return city; }
    public List<Screen> getScreens() { return screens; }
}