package com.hoanhtuan.boardingpasshdbank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class VietJetApiException extends RuntimeException{
    public VietJetApiException(String message) {
        super(message);
    }
}
