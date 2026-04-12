public class RetryPolicy {

    private final int maxRetries;

    public RetryPolicy(int maxRetries) {
        this.maxRetries = Math.max(0, maxRetries);
    }

    public boolean shouldRetry(int currentAttempt) {
        return currentAttempt < maxRetries;
    }
}