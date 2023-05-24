package vn.com.hdbank.boardingpasshdbank.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseInfo;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseController {
    public static <T> ResponseEntity<ResponseInfo<T>> exceptionResponseEntity(ApiResponseStatus apiResponseStatus,
                                                                              String requestId) {
        ResponseInfo<T> response = ResponseInfo.<T>builder()
                .responseId(requestId)
                .code(apiResponseStatus.getStatusCode())
                .message(apiResponseStatus.getStatusMessage())
                .build();
        MDC.clear();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
