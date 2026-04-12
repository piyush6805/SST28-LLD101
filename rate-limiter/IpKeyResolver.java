public class IpKeyResolver implements RateLimitKeyResolver {
    @Override
    public String resolve(RequestContext context) {
        return context.getIp();
    }
}