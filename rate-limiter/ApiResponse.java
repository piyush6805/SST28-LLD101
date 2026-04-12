public class ApiResponse {

    private final int statusCode;
    private final String message;

    public ApiResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return statusCode >= 200 && statusCode < 300;
    }

    @Override
    public String toString() {
        return "ApiResponse{statusCode=" + statusCode + ", message='" + message + "'}";
    }
}