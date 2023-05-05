package vn.com.hdbank.boardingpasshdbank.exception;

import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.controller.PassengerController;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseInfo;
import vn.com.hdbank.boardingpasshdbank.utils.WriteLog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String CLASS_NAME  = PassengerController.class.getName();

    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseInfo> handleVietJetApiException(CustomException ex) {
        // logging here
        final String METHOD_NAME = "handleVietJetApiException";
        WriteLog.errorLog(CLASS_NAME, METHOD_NAME, ex.getMessage());
        ResponseInfo response = ResponseInfo.builder()
                .statusCode(ex.getStatusCode())
                .statusMessage(ex.getStatusMessage())
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
                .statusCode(ApiResponseStatus.INTERNAL_SERVER_ERROR.getStatusCode())
                .statusMessage(ApiResponseStatus.INTERNAL_SERVER_ERROR.getStatusMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
