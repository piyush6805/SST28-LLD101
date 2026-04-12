public class RequestContext {

    private final String userId;
    private final String ip;
    private final String location;

    public RequestContext(String userId, String ip, String location) {
        this.userId = userId;
        this.ip = ip;
        this.location = location;
    }

    public String getUserId() {
        return userId;
    }

    public String getIp() {
        return ip;
    }

    public String getLocation() {
        return location;
    }
}