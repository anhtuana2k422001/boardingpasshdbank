package vn.com.hdbank.boardingpasshdbank.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseInfo;

public class ResponseEntityHelper {
    private ResponseEntityHelper() {}
    public static <T> ResponseEntity<ResponseInfo<T>> createSuccessResponseEntity(T data) {
        ResponseInfo<T> response = ResponseInfo.<T>builder()
                .data(data)
                .code(ApiResponseStatus.SUCCESS.getStatusCode())
                .message(ApiResponseStatus.SUCCESS.getStatusMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static <T> ResponseEntity<ResponseInfo<T>> createSuccessResponseEntity() {
        ResponseInfo<T> response = ResponseInfo.<T>builder()
                .code(ApiResponseStatus.SUCCESS.getStatusCode())
                .message(ApiResponseStatus.SUCCESS.getStatusMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static <T> ResponseEntity<ResponseInfo<T>> createErrorResponseEntity(
            ApiResponseStatus apiResponseStatus) {
        ResponseInfo<T> response = ResponseInfo.<T>builder()
                .code(apiResponseStatus.getStatusCode())
                .message(apiResponseStatus.getStatusMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
