public interface LimiterStrategy {
    boolean allow(String key);
}