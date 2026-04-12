import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FixedWindowLimiter implements LimiterStrategy {

    private static class Window {
        long start;
        int count;

        Window(long start) {
            this.start = start;
            this.count = 0;
        }
    }

    private final RateLimitConfig config;
    private final Map<String, Window> store = new ConcurrentHashMap<>();

    public FixedWindowLimiter(RateLimitConfig config) {
        this.config = config;
    }

    @Override
    public boolean allow(String key) {
        long now = System.currentTimeMillis();
        Window window = store.computeIfAbsent(key, ignored -> new Window(now));

        synchronized (window) {
            if (now - window.start >= config.getWindowMillis()) {
                window.start = now;
                window.count = 0;
            }

            if (window.count < config.getMaxRequests()) {
                window.count++;
                return true;
            }
            return false;
        }
    }
}