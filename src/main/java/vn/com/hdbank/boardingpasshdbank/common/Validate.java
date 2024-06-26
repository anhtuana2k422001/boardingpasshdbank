package vn.com.hdbank.boardingpasshdbank.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Validate {
    public static final String MESSAGE_NOT_EMPTY = "Trường thông tin không được để trống";
    public static final String MESSAGE_NOT_NULL = "Trường thông tin cầu bắt buộc";
    public static final String MESSAGE_FLIGHT_CODE = "Mã chuyến bay phải bắt đầu bằng 2 chữ cái và theo sau là 1 đến 4 chữ số";      /*Flight code must start with 2 letters and be followed by 1 to 4 digits*/
    public static final String REGEXP_FLIGHT_CODE = "^[A-Za-z]{2}\\d{1,4}$";
    public static final String MESSAGE_RESERVATION_CODE = "Mã đặt chỗ phải chứa tối đa 6 ký tự chữ và số";     /*Reservation code must contain up to 6 alphanumeric characters*/
    public static final String REGEXP_RESERVATION_CODE = "^[a-zA-Z0-9]{1,6}$";
    public static final String MESSAGE_SEATS = "Ghế phải có 1 hoặc 3 chữ số theo sau là một chữ cái";      /*Seats must have 1 or 3 digits followed by a letter*/
    public static final String REGEXP_SEATS = "^\\d{1,3}[a-zA-Z]$";
    public static final String MESSAGE_REQUIRED_ID = "Giá trị phải lơn hơn 0";
    public static final String MESSAGE_TOTAL_AMOUNT = "Tổng số tiền phải lớn hơn 0"; /*Total amount must be greater than zero*/
    public static final String MESSAGE_PRIZE_CODE = "Định dạng mã dự thưởng không hợp lệ";
    public static final String REGEX_PRIZE_CODE = "^VJ\\d{6}$";
    public static final String MESSAGE_PHONE_NUMBER = "Số điện thoại không hợp lệ";
    public static final String REGEX_PHONE_NUMBER = "^(\\+?84|0)\\d{9,10}$";
    public static final String MESSAGE_PASSWORD = "Mật khẩu phải có ít nhất 6 ký tự";
    public static final String REGEX_PASSWORD = "^.{6,}$";
}

