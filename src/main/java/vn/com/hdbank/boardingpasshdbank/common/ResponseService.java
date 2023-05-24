package vn.com.hdbank.boardingpasshdbank.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseInfo;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseService {
    public static <T> ResponseEntity<ResponseInfo<T>> successResponseEntity(ApiResponseStatus apiResponseStatus,
                                                                            T data, String requestId) {
        ResponseInfo<T> response = ResponseInfo.<T>builder()
                .responseId(requestId)
                .code(apiResponseStatus.getStatusCode())
                .message(apiResponseStatus.getStatusMessage())
                .data(data)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static <T> ResponseEntity<ResponseInfo<T>> successResponseEntity(String requestId) {
        ResponseInfo<T> response = ResponseInfo.<T>builder()
                .responseId(requestId)
                .code(ApiResponseStatus.SUCCESS.getStatusCode())
                .message(ApiResponseStatus.SUCCESS.getStatusMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static <T> ResponseEntity<ResponseInfo<T>> errorResponseEntity(ApiResponseStatus apiResponseStatus,
                                                                          String requestId) {
        ResponseInfo<T> response = ResponseInfo.<T>builder()
                .responseId(requestId)
                .code(apiResponseStatus.getStatusCode())
                .message(apiResponseStatus.getStatusMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static <T> ResponseEntity<ResponseInfo<T>> validateResponseEntity(Map<String, String> errors,
                                                                             String requestId) {
        ResponseInfo<T> response = ResponseInfo.<T>builder()
                .responseId(requestId)
                .code(ApiResponseStatus.VALIDATE_INVALID_INPUT .getStatusCode())
                .message(ApiResponseStatus.VALIDATE_INVALID_INPUT .getStatusMessage())
                .validate(errors)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
