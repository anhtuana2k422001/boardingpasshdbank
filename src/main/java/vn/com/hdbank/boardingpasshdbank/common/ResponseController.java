package vn.com.hdbank.boardingpasshdbank.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseInfo;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseController {

    public static <T> org.springframework.http.ResponseEntity<ResponseInfo<T>> responseEntity(ResponseInfo<T> response) {
        MDC.clear();
        return new org.springframework.http.ResponseEntity<>(response, HttpStatus.OK);
    }

    public static <T> org.springframework.http.ResponseEntity<ResponseInfo<T>> responseEntity(ApiResponseStatus apiResponseStatus,
                                                                                              String requestId) {
        MDC.clear();
        return new org.springframework.http.ResponseEntity<>(ResponseInfo.<T>builder()
                .responseId(requestId)
                .code(apiResponseStatus.getStatusCode())
                .message(apiResponseStatus.getStatusMessage())
                .build(), HttpStatus.OK);
    }

}
