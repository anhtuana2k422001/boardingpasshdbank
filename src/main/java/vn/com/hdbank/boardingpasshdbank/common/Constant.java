package vn.com.hdbank.boardingpasshdbank.common;

import java.time.LocalDateTime;
public class Constant {
    private Constant(){}
    public static final String REQUEST = "Request: {}";
    public static final String RESPONSE = "Response: {}";
    public static final LocalDateTime VJ_ESKYONE_START_DATE = LocalDateTime.parse("2023-01-01T00:00:00");
    public static final LocalDateTime VJ_ESKYONE_END_DATE = LocalDateTime.parse("2025-01-01T00:00:00");
}
