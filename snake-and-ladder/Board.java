import java.util.*;

public class Board {
    private final int size;
    private final List<Snake> snakes;
    private final List<Ladder> ladders;

    public Board(int size) {
        if (size < 4) {
            throw new IllegalArgumentException("Board size must be at least 4.");
        }
        this.size = size;
        this.snakes = new ArrayList<Snake>();
        this.ladders = new ArrayList<Ladder>();
    }

    public int getSize() {
        return size;
    }

    public int getLastCell() {
        return size * size;
    }

    public List<Snake> getSnakes() {
        return snakes;
    }

    public List<Ladder> getLadders() {
        return ladders;
    }

    public int getNewPosition(int position) {
        for (Snake snake : snakes) {
            if (snake.getStart() == position) {
                return snake.getEnd();
            }
        }

        for (Ladder ladder : ladders) {
            if (ladder.getStart() == position) {
                return ladder.getEnd();
            }
        }

        return position;
    }

    public String describeTransition(int beforeJump, int afterJump) {
        if (beforeJump == afterJump) {
            return "No snake or ladder.";
        }
        if (afterJump < beforeJump) {
            return "Bitten by a snake: " + beforeJump + " -> " + afterJump;
        }
        return "Climbed a ladder: " + beforeJump + " -> " + afterJump;
    }

    public void placeRandomSnakesAndLadders(int countEach) {
        int maxCell = getLastCell();
        int maxSpecial = Math.max(1, (maxCell - 2) / 3);
        if (countEach > maxSpecial) {
            throw new IllegalArgumentException("Too many snakes/ladders for this board size.");
        }

        Random random = new Random();
        Set<Integer> usedStart = new HashSet<Integer>();
        Set<Integer> usedEnd = new HashSet<Integer>();
        Map<Integer, Integer> edges = new HashMap<Integer, Integer>();

        int snakesAdded = 0;
        int attempts = 0;
        int maxAttempts = 200000;
        while (snakesAdded < countEach && attempts < maxAttempts) {
            attempts++;
            int head = 2 + random.nextInt(maxCell - 2);
            int tail = 1 + random.nextInt(head - 1);

            if (usedStart.contains(head) || usedEnd.contains(tail) || head == tail) {
                continue;
            }
            if (edges.containsKey(head)) {
                continue;
            }
            if (createsCycle(edges, head, tail)) {
                continue;
            }

            snakes.add(new Snake(head, tail));
            usedStart.add(head);
            usedEnd.add(tail);
            edges.put(head, tail);
            snakesAdded++;
        }

        int laddersAdded = 0;
        attempts = 0;
        while (laddersAdded < countEach && attempts < maxAttempts) {
            attempts++;
            int start = 1 + random.nextInt(maxCell - 2);
            int end = start + 1 + random.nextInt(maxCell - start);

            if (usedStart.contains(start) || usedEnd.contains(end) || start == end) {
                continue;
            }
            if (edges.containsKey(start)) {
                continue;
            }
            if (createsCycle(edges, start, end)) {
                continue;
            }

            ladders.add(new Ladder(start, end));
            usedStart.add(start);
            usedEnd.add(end);
            edges.put(start, end);
            laddersAdded++;
        }

        if (snakesAdded < countEach || laddersAdded < countEach) {
            throw new IllegalStateException("Could not place all snakes/ladders without conflicts.");
        }
    }

    private boolean createsCycle(Map<Integer, Integer> edges, int from, int to) {
        if (from == to) {
            return true;
        }

        int current = to;
        Set<Integer> seen = new HashSet<Integer>();
        while (true) {
            if (current == from) {
                return true;
            }
            if (!edges.containsKey(current)) {
                return false;
            }
            if (seen.contains(current)) {
                return true;
            }
            seen.add(current);
            current = edges.get(current);
        }
    }
}
