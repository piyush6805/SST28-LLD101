public interface RateLimitKeyResolver {
    String resolve(RequestContext context);
}