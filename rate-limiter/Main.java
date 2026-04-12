public class Main {
    public static void main(String[] args) {
        runDemo(RateLimitType.FIXED);
        runDemo(RateLimitType.SLIDING);
    }

    private static void runDemo(RateLimitType type) {
        System.out.println("\nUsing " + type + " window limiter");

        RateLimitConfig config = new RateLimitConfig(3, 5_000);
        Limiter limiter = new Limiter(LimiterFactory.create(type, config));
        RemoteResourceApi remote = new RemoteResourceApiImpl();
        RemoteResourceApi proxy = new RateLimitedRemoteResourceProxy(remote, limiter, new UserKeyResolver());

        RequestService service = new RequestService(proxy);
        ApiHandler api = new ApiHandler(service);

        RequestContext user1 = new RequestContext("user1", "10.1.1.8", "IN");
        RequestContext user2 = new RequestContext("user2", "10.1.1.9", "IN");

        print("user1 local", api.execute(user1, false));

        for (int i = 1; i <= 5; i++) {
            print("user1 remote " + i, api.execute(user1, true));
        }

        print("user2 remote 1", api.execute(user2, true));
    }

    private static void print(String label, ApiResponse response) {
        System.out.println(label + " -> " + response.getStatusCode() + " | " + response.getMessage());
    }
}