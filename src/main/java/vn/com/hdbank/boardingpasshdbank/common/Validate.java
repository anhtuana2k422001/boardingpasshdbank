package vn.com.hdbank.boardingpasshdbank.common;

public class Validate {
    private Validate(){}
    public static final String  MESSAGE_NOT_EMPTY = "Không được để trống";
    public static final String  MESSAGE_NOT_NULL = "Trường thông tin cầu bắt buộc";
    public static final String  MESSAGE_FLIGHT_CODE = "Mã chuyến bay phải bắt đầu bằng 2 chữ cái và theo sau là 1 đến 4 chữ số";      /*Flight code must start with 2 letters and be followed by 1 to 4 digits*/
    public static final String  REGEXP_FLIGHT_CODE = "^[A-Za-z]{2}\\d{1,4}$";
    public static final String  MESSAGE_RESERVATION_CODE = "Mã đặt chỗ phải chứa tối đa 6 ký tự chữ và số";     /*Reservation code must contain up to 6 alphanumeric characters*/
    public static final String  REGEXP_RESERVATION_CODE = "^[a-zA-Z0-9]{1,6}$";
    public static final String  MESSAGE_SEATS = "Ghế phải có 1 hoặc 3 chữ số theo sau là một chữ cái";      /*Seats must have 1 or 3 digits followed by a letter*/
    public static final String  REGEXP_SEATS = "^\\d{1,3}[a-zA-Z]$";

}

