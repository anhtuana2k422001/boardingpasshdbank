package vn.com.hdbank.boardingpasshdbank.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constant {
    public static final String REQUEST = "Request: {}";
    public static final String RESPONSE = "Response: {}";
    public static final String OUTGOING_REQUEST = "Outgoing Request: {}";
    public static final String OUTGOING_RESPONSE = "Outgoing Response: {}";
    public static final String ERROR = "Error: ";
    public static final String FORMAT_LOG = "Error: code: {}, message: {}";
    public static final String ERROR_JSON_TO_STRING = "Error Json to String: ";
    public static final String ERROR_FROM_JSON_STRING = "Error Json to object:  ";
    public static final String ERROR_VALIDATE = "Validation errors: {}";
    public static final String REQUEST_ID = "requestId";
    public static final String URL = "URL:";
    public static final String METHOD = "Method:";
    public static final String HEADER = "Header:";
    public static final String COMMA_SPACE = ", ";
    public static final LocalDateTime VJ_E_SKY_ONE_START_DATE = LocalDateTime.parse("2023-01-01T00:00:00");
    public static final LocalDateTime VJ_E_SKY_ONE_END_DATE = LocalDateTime.parse("2025-01-01T00:00:00");
    public static final String RESERVATION_LOCATOR = "reservationLocator";
    public static final String AIRLINE_CODE = "airlineCode";
    public static final String FLIGHT_NUMBER = "flightNumber";
    public static final String SEAT_ROW = "seatRow";
    public static final String SEAT_COLS = "seatCols";
    public static final String PASSENGER_FIRST_NAME = "passengerFirstName";
    public static final String PASSENGER_LAST_NAME = "passengerLastName";
    public static final String AMOUNT_FA = "FA";
    public static final String VIET_JET_LUCKY_CONTENT = "Hoàn tiền chương trình Vietjet Daily Lucky";
    public static final String  BANK_ACCOUNT  = "067804070161483";
    public static final String BALANCE_AFTER_TRANSACTION = "101200000";
    public static final String LINK_WEB_PRIZES = "https://link-webview-prizes.com";

}
