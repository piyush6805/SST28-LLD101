public class LimiterFactory {

    public static LimiterStrategy create(RateLimitType type, RateLimitConfig config) {

        if (type == RateLimitType.FIXED) {
            return new FixedWindowLimiter(config);
        }
        if (type == RateLimitType.SLIDING) {
            return new SlidingWindowLimiter(config);
        }

        throw new IllegalArgumentException("Unknown type");
    }
}