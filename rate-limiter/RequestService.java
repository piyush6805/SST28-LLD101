public class RequestService {

    private final RemoteResourceApi remoteResourceApi;

    public RequestService(RemoteResourceApi remoteResourceApi) {
        this.remoteResourceApi = remoteResourceApi;
    }

    public ApiResponse process(RequestContext context, boolean needsExternalCall) {

        if (!needsExternalCall) {
            return new ApiResponse(200, "skipped remote call");
        }

        return remoteResourceApi.call(context);
    }
}