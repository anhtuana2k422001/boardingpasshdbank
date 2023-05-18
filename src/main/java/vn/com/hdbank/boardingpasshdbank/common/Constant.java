package vn.com.hdbank.boardingpasshdbank.common;

import java.time.LocalDateTime;
public class Constant {
    private Constant(){}
    public static final String REQUEST = "Request: {}";
    public static final String RESPONSE = "Response: {}";
    public static final LocalDateTime VJ_E_SKY_ONE_START_DATE = LocalDateTime.parse("2023-01-01T00:00:00");
    public static final LocalDateTime VJ_E_SKY_ONE_END_DATE = LocalDateTime.parse("2025-01-01T00:00:00");

    public static final String VIET_JET_LUCKY_CONTENT = "Hoàn tiền chương trình VietJet Daily Lucky";
}
