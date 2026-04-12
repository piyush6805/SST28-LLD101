import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SlidingWindowLimiter implements LimiterStrategy {

    private final RateLimitConfig config;
    private final Map<String, Deque<Long>> store = new ConcurrentHashMap<>();

    public SlidingWindowLimiter(RateLimitConfig config) {
        this.config = config;
    }

    @Override
    public boolean allow(String key) {
        long now = System.currentTimeMillis();
        Deque<Long> q = store.computeIfAbsent(key, ignored -> new LinkedList<>());

        synchronized (q) {
            while (!q.isEmpty() && now - q.peekFirst() >= config.getWindowMillis()) {
                q.pollFirst();
            }

            if (q.size() < config.getMaxRequests()) {
                q.addLast(now);
                return true;
            }
            return false;
        }
    }
}