import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Board size (n x n): ");
        int boardSize = input.nextInt();

        System.out.print("Number of players: ");
        int playerCount = input.nextInt();

        System.out.print("Difficulty (easy/hard): ");
        String difficulty = input.next().trim().toLowerCase();

        Board board = new Board(boardSize);
        board.placeRandomSnakesAndLadders(boardSize);

        List<Player> players = new ArrayList<Player>();
        for (int i = 1; i <= playerCount; i++) {
            players.add(new Player(i));
        }

        MakeMoveStrategy strategy = "hard".equals(difficulty) ? 
            new SkipOnThreeConsecutiveSixes() : 
            new AllowConsecutiveSixes();

        input.close();

        Game game = new Game(board, players, strategy);
        game.startGame();
    }
}