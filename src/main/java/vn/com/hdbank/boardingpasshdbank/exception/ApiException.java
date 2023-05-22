package vn.com.hdbank.boardingpasshdbank.exception;

import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;

public class ApiException extends RuntimeException{
    private final ApiResponseStatus apiResponseStatus;
    public ApiException(ApiResponseStatus apiResponseStatus) {
        super(apiResponseStatus.getStatusMessage());
        this.apiResponseStatus = apiResponseStatus;
    }

    public String getStatusCode() {
        return apiResponseStatus.getStatusCode();
    }

    public String getStatusMessage() {
        return apiResponseStatus.getStatusMessage();
    }

}
