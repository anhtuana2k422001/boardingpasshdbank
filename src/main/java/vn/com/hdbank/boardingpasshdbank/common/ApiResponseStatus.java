package vn.com.hdbank.boardingpasshdbank.common;

public enum ApiResponseStatus {

    INTERNAL_SERVER_ERROR("500", "Internal Server Error"),
    SUCCESS("000", "Successfully"),
    BAD_REQUEST("001", "Bad Request"),

    UNAUTHORIZED("002", "Unauthorized"),

    FORBIDDEN("003", "Forbidden"),

    NOT_FOUND("004", "Not Found"),

    METHOD_NOT_ALLOWED("005", "Method Not Allowed"),

    CONFLICT("006", "Conflict"),

    SERVICE_UNAVAILABLE("007", "Service Unavailable"),
    PASSENGER_EXISTED("008", "Passenger already existed"),
    VIETJET_API_ERROR("009", "Unable to call Vietjet API"),
    EXTERNAL_API_ERROR("010", "Error calling external API"),

    ;

    private final String statusCode;
    private final String statusMessage;

    ApiResponseStatus(String statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}
