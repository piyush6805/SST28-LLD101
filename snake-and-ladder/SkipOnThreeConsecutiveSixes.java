import java.util.HashMap;
import java.util.Map;

public class SkipOnThreeConsecutiveSixes implements MakeMoveStrategy {
    private final Map<Integer, Integer> consecutiveSixCountByPlayer = new HashMap<Integer, Integer>();

    @Override
    public MoveResult makeMove(Player player, int diceValue, Board board) {
        int playerId = player.getId();
        int sixCount = consecutiveSixCountByPlayer.getOrDefault(playerId, 0);

        if (diceValue == 6) {
            sixCount++;
            consecutiveSixCountByPlayer.put(playerId, sixCount);

            if (sixCount >= 3) {
                consecutiveSixCountByPlayer.put(playerId, 0);
                return new MoveResult(player.getPosition(), false, "Three consecutive sixes: turn skipped.");
            }
        } else {
            consecutiveSixCountByPlayer.put(playerId, 0);
        }

        int current = player.getPosition();
        int target = current + diceValue;

        if (target > board.getLastCell()) {
            return new MoveResult(current, false, "Move skipped: exceeds board limit.");
        }

        int resolved = board.getNewPosition(target);
        boolean extraTurn = diceValue == 6;
        String note = board.describeTransition(target, resolved);
        return new MoveResult(resolved, extraTurn, note);
    }
}
