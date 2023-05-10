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

    TICKET_VIETJET_EXISTED_AND_ASSIGNED("009", "Ticket Vietjet already exists and has been assigned to a user"),
    VIETJET_API_ERROR("010", "Unable to call Vietjet API"),
    EXTERNAL_API_ERROR("011", "Error calling external API"),
    INVALID_INPUT("012", "Invalid input"),
    INVALID_TICKET("013", "Invalid ticket information"),
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
