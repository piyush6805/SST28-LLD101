public class ShowSeat {
    private final Seat seat;
    private SeatStatus status;
    private double price;

    private String lockedBy;
    private long lockTime;

    public ShowSeat(Seat seat) {
        this.seat = seat;
        this.status = SeatStatus.AVAILABLE;
    }

    public synchronized boolean isAvailable() {
        return status == SeatStatus.AVAILABLE;
    }

    public synchronized void lock(String userId) {
        this.status = SeatStatus.LOCKED;
        this.lockedBy = userId;
        this.lockTime = System.currentTimeMillis();
    }

    public synchronized void book() {
        this.status = SeatStatus.BOOKED;
    }

    public synchronized void unlock() {
        this.status = SeatStatus.AVAILABLE;
        this.lockedBy = null;
    }

    public long getLockTime() { return lockTime; }
    public SeatStatus getStatus() { return status; }

    public void setPrice(double price) { this.price = price; }
    public double getPrice() { return price; }
}