public class MoveResult {
    private final int finalPosition;
    private final boolean extraTurn;
    private final String note;

    public MoveResult(int finalPosition, boolean extraTurn, String note) {
        this.finalPosition = finalPosition;
        this.extraTurn = extraTurn;
        this.note = note;
    }

    public int getFinalPosition() {
        return finalPosition;
    }

    public boolean hasExtraTurn() {
        return extraTurn;
    }

    public String getNote() {
        return note;
    }
}
