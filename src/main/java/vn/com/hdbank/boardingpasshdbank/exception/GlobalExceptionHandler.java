package vn.com.hdbank.boardingpasshdbank.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.controller.TicketVietjetController;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseInfo;
import vn.com.hdbank.boardingpasshdbank.utils.WriteLog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice

public class GlobalExceptionHandler {
    private static final String CLASS_NAME  = TicketVietjetController.class.getName();
    private static final Logger LOGGER  = LoggerFactory.getLogger(GlobalExceptionHandler.class);



    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseInfo> handleVietJetApiException(CustomException ex) {
        // logging here
        final String METHOD_NAME = "handleVietJetApiException";
        WriteLog.errorLog(CLASS_NAME, METHOD_NAME, ex.getMessage());
        ResponseInfo response = ResponseInfo.builder()
                .code(ex.getStatusCode())
                .message(ex.getStatusMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseInfo> exception(Exception ex) {
        // logging here
        final String METHOD_NAME = "exception";
        WriteLog.errorLog(CLASS_NAME, METHOD_NAME, ex.getMessage());
        ResponseInfo response = ResponseInfo.builder()
                .code(ApiResponseStatus.INTERNAL_SERVER_ERROR.getStatusCode())
                .message(ApiResponseStatus.INTERNAL_SERVER_ERROR.getStatusMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
