public class Limiter {

    private volatile LimiterStrategy strategy;

    public Limiter(LimiterStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean check(String key) {
        return strategy.allow(key);
    }

    public void setStrategy(LimiterStrategy strategy) {
        this.strategy = strategy;
    }
}