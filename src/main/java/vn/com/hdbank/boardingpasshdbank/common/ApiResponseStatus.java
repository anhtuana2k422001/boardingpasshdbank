package vn.com.hdbank.boardingpasshdbank.common;

public enum ApiResponseStatus {
    SUCCESS("000", "Thành công"), /* Successfully */
    INTERNAL_SERVER_ERROR("001", "Lỗi máy chủ nội bộ"), /*Internal Server Error*/
    DUPLICATED("002", "Trùng lặp"), /*Duplicated */
    VIET_JET_EXISTED_AND_ASSIGNED("003", "Ticket Vietjet already exists and has been assigned to a user"),
    VIET_JET_API_ERROR("004", "Không gọi được Vietjet API"), /*Unable to call VietJet API*/
    EXTERNAL_API_ERROR("005", "Lỗi khi gọi API bên ngoài"),  /*Error calling external API*/
    VALIDATE_INVALID_INPUT("006", "Đầu vào không hợp lệ"), /*Invalid input*/
    INVALID_TICKET("007", "Thông tin vé không hợp lệ"), /*Invalid ticket information */
    CUSTOMER_NOT_VIET_JET("008", "Khách hàng không phải là hành khách của Vietjet"),  /* Customer is not a VietJet passenger */
    NOT_ENOUGH_CONDITION_FOR_PRIZE("009", "Khách hàng không đủ điều kiện tham gia quay thưởng"), /*Customers are not eligible to participate in the drawing*/
    NOT_FOUND_CUSTOMER("010", "Không tìm thấy khách hàng"), /*No customers found*/
    PROGRAM_ENDED("011", "Chương trình quay thưởng đã dừng"), /*The prize draw has stopped*/
    NO_PRIZE_CODE("012", "Khách hàng chưa có mã dự thưởng"), /*Customers do not have a coupon code*/
    UPDATE_PRIZE_ERROR ("013","Cập nhật thông tin vé không thành công"),  /*Update ticket information failed */
    ;

    private final String statusCode;
    private final String statusMessage;

    ApiResponseStatus(String statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}
