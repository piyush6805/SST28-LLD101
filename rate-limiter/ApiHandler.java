public class ApiHandler {

    private final RequestService service;

    public ApiHandler(RequestService service) {
        this.service = service;
    }

    public ApiResponse execute(RequestContext context, boolean needsExternalCall) {
        return service.process(context, needsExternalCall);
    }
}