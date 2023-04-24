package com.hoanhtuan.boardingpasshdbank.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseToken {
    private String token;
    private String expiration;
}
