public class UserKeyResolver implements RateLimitKeyResolver {
    @Override
    public String resolve(RequestContext context) {
        return context.getUserId();
    }
}