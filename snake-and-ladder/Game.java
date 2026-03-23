import java.util.*;

public class Game {
    private final Board board;
    private final LinkedList<Player> players;
    private final MakeMoveStrategy moveStrategy;
    private final List<Player> winners;

    public Game(Board board, List<Player> players, MakeMoveStrategy moveStrategy) {
        if (board == null) {
            throw new IllegalArgumentException("Board cannot be null.");
        }
        if (players == null || players.size() < 2) {
            throw new IllegalArgumentException("At least 2 players are required.");
        }
        if (moveStrategy == null) {
            throw new IllegalArgumentException("Move strategy cannot be null.");
        }

        this.board = board;
        this.players = new LinkedList<Player>(players);
        this.moveStrategy = moveStrategy;
        this.winners = new ArrayList<Player>();
    }

    public void startGame() {
        System.out.println("Board size: " + board.getSize() + "x" + board.getSize());
        System.out.println("Snakes: " + board.getSnakes().size() + ", Ladders: " + board.getLadders().size());
        printBoardSetup();
        System.out.println();

        while (players.size() > 1) {
            makeMove();
        }

        if (!players.isEmpty()) {
            Player lastPlayer = players.poll();
            System.out.println(lastPlayer.getName() + " is the last remaining player.");
        }

        if (!winners.isEmpty()) {
            System.out.print("Winners order: ");
            for (int i = 0; i < winners.size(); i++) {
                System.out.print(winners.get(i).getName());
                if (i < winners.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }

    public void makeMove() {
        Player current = players.poll();
        if (current == null) {
            return;
        }

        boolean keepRolling = true;
        while (keepRolling) {
            int diceValue = Dice.roll();
            MoveResult result = moveStrategy.makeMove(current, diceValue, board);
            current.setPosition(result.getFinalPosition());

            System.out.println(current.getName() + " rolled " + diceValue + ", moved to " + current.getPosition() + ". " + result.getNote());

            if (current.getPosition() == board.getLastCell()) {
                winners.add(current);
                System.out.println(current.getName() + " reached " + board.getLastCell() + " and won.");
                return;
            }

            keepRolling = result.hasExtraTurn();
        }

        players.offer(current);
    }

    private void printBoardSetup() {
        System.out.println("Snake positions (head->tail):");
        for (Snake snake : board.getSnakes()) {
            System.out.println("  " + snake.getStart() + " -> " + snake.getEnd());
        }

        System.out.println("Ladder positions (start->end):");
        for (Ladder ladder : board.getLadders()) {
            System.out.println("  " + ladder.getStart() + " -> " + ladder.getEnd());
        }
    }
}
