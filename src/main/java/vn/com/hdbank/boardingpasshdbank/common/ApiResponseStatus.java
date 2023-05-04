package vn.com.hdbank.boardingpasshdbank.common;

public enum ApiResponseStatus {

    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    SUCCESS(1000, "Successfully!"),
    CREATED(1001,"Created Successfully!"),
    DELETED(1002,"Deleted Successfully!"),
    UNAUTHORIZED(1101, "Unauthorized"),
    NOT_FOUND(1104, "Not Found"),

    MISSING_REQUIRED_FIELD(1105, "Missing required field"),

    INVALID_CREDENTIALS(1106, "Invalid credentials");

    private final int statusCode;
    private final String statusMessage;

    ApiResponseStatus(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}
