import java.util.*;
public class SeatLockService {

    private static final long LOCK_TIMEOUT = 5 * 60 * 1000;

    public boolean lockSeats(String userId, List<ShowSeat> seats) {
        for (ShowSeat seat : seats) {
            synchronized (seat) {
                if (!isAvailable(seat)) return false;
                seat.lock(userId);
            }
        }
        return true;
    }

    private boolean isAvailable(ShowSeat seat) {
        if (seat.getStatus() == SeatStatus.AVAILABLE) return true;

        if (seat.getStatus() == SeatStatus.LOCKED) {
            if (System.currentTimeMillis() - seat.getLockTime() > LOCK_TIMEOUT) {
                seat.unlock();
                return true;
            }
        }
        return false;
    }
}