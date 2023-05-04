package com.hoanhtuan.boardingpasshdbank.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    RESULT_CODE_SUCCESS("000", "Successful"),
    RESULT_CODE_UNAUTHORIZED("001", "Unauthorized"),
    RESULT_CODE_FORBIDDEN("002", "Forbidden"),
    RESULT_CODE_NOT_FOUND("003", "Not found"),
    RESULT_CODE_SERVER_ERROR("004", "Internal Server Error"),
    RESULT_CODE_INVALID_TOKEN("005", "Invalid Token"),
    RESULT_CODE_TOKEN_NOT_FOUND("006", "Token does not exist"),
    // VALIDATE
    HD_BANK_VALIDATE_100("100", "Invalid fullName"),
    HD_BANK_VALIDATE_101("101", "Invalid reservationCode"),
    HD_BANK_VALIDATE_102("102", "Invalid flightCode"),
    HD_BANK_VALIDATE_103("103", "Invalid seats");
    private final String statusCode;
    private final String statusMessage;
}
