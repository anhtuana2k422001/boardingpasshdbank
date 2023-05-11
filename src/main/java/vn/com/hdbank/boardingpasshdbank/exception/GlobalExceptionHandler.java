package vn.com.hdbank.boardingpasshdbank.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.com.hdbank.boardingpasshdbank.utils.JsonUtils;


@RestControllerAdvice

public class GlobalExceptionHandler {
    private static final Logger LOGGER  = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseError> handleVietJetApiException(CustomException ex) {
        ResponseError response = ResponseError.builder()
                .code(ex.getStatusCode())
                .message(ex.getStatusMessage())
                .build();
        LOGGER.error("Error: ", ex);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseError> exception(Exception ex) {
        ResponseError response = ResponseError.builder()
                .code(ApiResponseStatus.INTERNAL_SERVER_ERROR.getStatusCode())
                .message(ApiResponseStatus.INTERNAL_SERVER_ERROR.getStatusMessage())
                .build();
        LOGGER.error("Error: ", ex);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
