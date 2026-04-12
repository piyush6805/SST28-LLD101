public class RemoteResourceApiImpl implements RemoteResourceApi {
    @Override
    public ApiResponse call(RequestContext context) {
        String msg = "remote call ok for " + context.getUserId();
        return new ApiResponse(200, msg);
    }
}