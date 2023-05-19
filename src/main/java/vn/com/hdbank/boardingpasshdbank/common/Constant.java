package vn.com.hdbank.boardingpasshdbank.common;

import java.time.LocalDateTime;
public class Constant {
    private Constant(){}
    public static final String REQUEST = "Request: {}";
    public static final String RESPONSE = "Response: {}";
    public static final LocalDateTime VJ_E_SKY_ONE_START_DATE = LocalDateTime.parse("2023-01-01T00:00:00");
    public static final LocalDateTime VJ_E_SKY_ONE_END_DATE = LocalDateTime.parse("2025-01-01T00:00:00");
    public static final String CUSTOMER_PRIZE_SUCCESS = "Khách hàng đã quay số dự thưởng và nhận hoàn tiền thành công";
    public static final String PRIZE_SUCCESS_NOT_DIALED = "Khách hàng đã được cấp số dự thưởng nhưng chưa tham gia quay số";
    public static final String VIET_JET_LUCKY_CONTENT = "Hoàn tiền chương trình VietJet Daily Lucky";
    public static final String  BANK_ACCOUNT  = "067804070161483";
    public static final double BALANCE_AFTER_TRANSACTION = 101200000;
    public static final String LINK_WEB_PRIZES = "https://link-webview-prizes.com";

}
