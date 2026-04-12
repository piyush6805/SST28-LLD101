public class RateLimitedRemoteResourceProxy implements RemoteResourceApi {

    private final RemoteResourceApi target;
    private final Limiter limiter;
    private final RateLimitKeyResolver keyResolver;

    public RateLimitedRemoteResourceProxy(RemoteResourceApi target,
                                          Limiter limiter,
                                          RateLimitKeyResolver keyResolver) {
        this.target = target;
        this.limiter = limiter;
        this.keyResolver = keyResolver;
    }

    @Override
    public ApiResponse call(RequestContext context) {
        String key = keyResolver.resolve(context);
        if (!limiter.check(key)) {
            return new ApiResponse(429, "rate limit hit for " + key);
        }
        return target.call(context);
    }
}