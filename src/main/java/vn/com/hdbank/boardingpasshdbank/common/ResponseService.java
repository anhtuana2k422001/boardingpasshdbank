package vn.com.hdbank.boardingpasshdbank.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseInfo;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseService {

    public static <T> ResponseInfo<T> successResponse(ApiResponseStatus apiResponseStatus, T data, String requestId) {
        return  ResponseInfo.<T>builder()
                .responseId(requestId)
                .code(apiResponseStatus.getStatusCode())
                .message(apiResponseStatus.getStatusMessage())
                .data(data)
                .build();
    }

    public static <T> ResponseInfo<T> successResponse(ApiResponseStatus apiResponseStatus, String requestId) {
        return  ResponseInfo.<T>builder()
                .responseId(requestId)
                .code(apiResponseStatus.getStatusCode())
                .message(apiResponseStatus.getStatusMessage())
                .build();
    }

    public static <T> ResponseInfo<T> errorResponse(ApiResponseStatus apiResponseStatus, String requestId) {
        return  ResponseInfo.<T>builder()
                .responseId(requestId)
                .code(apiResponseStatus.getStatusCode())
                .message(apiResponseStatus.getStatusMessage())
                .build();
    }

    public static <T> ResponseInfo<T> validateResponse(Map<String, String> errors, String requestId) {
        return ResponseInfo.<T>builder()
                .responseId(requestId)
                .code(ApiResponseStatus.VALIDATE_INVALID_INPUT.getStatusCode())
                .message(ApiResponseStatus.VALIDATE_INVALID_INPUT.getStatusMessage())
                .validate(errors)
                .build();
    }

}
