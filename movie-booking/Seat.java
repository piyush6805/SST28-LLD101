public class Seat {
    private final String seatId;
    private final int row;
    private final SeatType type;

    public Seat(String seatId, int row, SeatType type) {
        this.seatId = seatId;
        this.row = row;
        this.type = type;
    }

    public String getSeatId() { return seatId; }
    public SeatType getType() { return type; }
}