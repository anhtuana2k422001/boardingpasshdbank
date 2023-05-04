package vn.com.hdbank.boardingpasshdbank.exception;

import lombok.Getter;
import lombok.Setter;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;

@Getter
@Setter
public class CustomException extends RuntimeException{
    ApiResponseStatus apiResponseStatus;
    public CustomException(ApiResponseStatus apiResponseStatus) {
        super(apiResponseStatus.getStatusMessage());
        this.apiResponseStatus = apiResponseStatus;
    }
}
