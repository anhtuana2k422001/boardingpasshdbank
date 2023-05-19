package vn.com.hdbank.boardingpasshdbank.common;

import lombok.Getter;

@Getter
public enum Validate {
    FLIGHT_CODE("^[A-Za-z]{2}\\d{1,4}$", "Mã chuyến bay phải bắt đầu bằng 2 chữ cái và theo sau là 1 đến 4 chữ số"),
    ;

    private final String regexp;
    private final String message;

    Validate(String regexp, String message) {
        this.regexp = regexp;
        this.message = message;
    }

    public String getRegexp() {
        return regexp;
    }

    public String getMessage() {
        return message;
    }
}
