public class AllowConsecutiveSixes implements MakeMoveStrategy {
    @Override
    public MoveResult makeMove(Player player, int diceValue, Board board) {
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
