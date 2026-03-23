import java.util.*;

public class Dice {
    private static final Random RANDOM = new Random();

    private Dice() {
    }

    public static int roll() {
        return RANDOM.nextInt(6) + 1;
    }
}
