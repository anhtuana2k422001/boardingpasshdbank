package com.hoanhtuan.boardingpasshdbank.exception;

import com.hoanhtuan.boardingpasshdbank.common.Constant;
import com.hoanhtuan.boardingpasshdbank.model.response.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessage> handleIOException(IOException ex) {
        // Logging error here
        ErrorMessage response = ErrorMessage.builder()
                .responseCode(Constant.INTERNAL_SERVER_ERROR)
                .responseMessage(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(VietJetApiException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessage> handleVietJetApiException(VietJetApiException ex) {
        // logging here
        ErrorMessage response = ErrorMessage.builder()
                .responseCode(Constant.INTERNAL_SERVER_ERROR)
                .responseMessage(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorMessage> handleAuthenticationException(AuthenticationException ex) {
        // logging here
        ErrorMessage response = ErrorMessage.builder()
                .responseCode(Constant.UNAUTHORIZED)
                .responseMessage(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorMessage> handleForbiddenException(ForbiddenException ex) {
        // logging here
        ErrorMessage response = ErrorMessage.builder()
                .responseCode(Constant.FORBIDDEN)
                .responseMessage(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> handleNotFoundException(NotFoundException ex) {
        // logging here
        ErrorMessage response = ErrorMessage.builder()
                .responseCode(Constant.NOT_FOUND)
                .responseMessage(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessage> exception(Exception ex) {
        // logging here
        ErrorMessage response = ErrorMessage.builder()
                .responseCode(Constant.INTERNAL_SERVER_ERROR)
                .responseMessage(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
