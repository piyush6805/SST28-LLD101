public class LocationKeyResolver implements RateLimitKeyResolver {
    @Override
    public String resolve(RequestContext context) {
        return context.getLocation();
    }
}