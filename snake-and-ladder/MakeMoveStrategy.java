public interface MakeMoveStrategy {
    MoveResult makeMove(Player player, int diceValue, Board board);
}
