package vn.com.hdbank.boardingpasshdbank.common;

public enum ApiResponseStatus {
    SUCCESS("000", "Thành công"), /* Successfully */
    INTERNAL_SERVER_ERROR("001", "Lỗi máy chủ nội bộ"), /*Internal Server Error*/
    DUPLICATED("002", "Trùng lặp"), /*Duplicated */
    VIET_JET_EXISTED_AND_ASSIGNED("003", "Vé đã tồn tại và được gắn với 1 khách hàng"), /* Ticket already exists and is tied to 1 customer*/
    VIET_JET_API_ERROR("004", "Không gọi được Vietjet API"), /*Unable to call DatabaseValidation API*/
    EXTERNAL_API_ERROR("005", "Lỗi khi gọi API bên ngoài"),  /*Error calling external API*/
    VALIDATE_INVALID_INPUT("006", "Đầu vào không hợp lệ"), /*Invalid input*/
    INVALID_TICKET("007", "Thông tin vé không hợp lệ"), /*Invalid ticket information */
    CUSTOMER_NOT_VIET_JET("008", "Khách hàng không phải là hành khách của Vietjet"),  /* Customer is not a DatabaseValidation passenger */
    NOT_ENOUGH_CONDITION_FOR_PRIZE("009", "Khách hàng không đủ điều kiện tham gia quay thưởng"), /*Customers are not eligible to participate in the drawing*/
    NOT_FOUND_CUSTOMER("010", "Không tìm thấy khách hàng"), /*No customers found*/
    PROGRAM_ENDED("011", "Chương trình quay thưởng đã dừng"), /*The prize draw has stopped*/
    NO_PRIZE_CODE("012", "Khách hàng chưa có mã dự thưởng"), /*Customers do not have a coupon code*/
    UPDATE_PRIZE_ERROR ("013","Cập nhật thông tin vé không thành công"),  /*Update ticket information failed */
    INVALID_CLIENT_REQUEST("014","Yêu cầu ứng dụng khách không hợp lệ"), /*Invalid client request*/
    DATABASE_ERROR("015", "Lỗi cơ sở dữ liệu"), /*Database error*/
    PRIZE_CODE_ERROR("016", "Mã dự thưởng của khách hàng không chính xác"), /*Customer's prize code is incorrect*/
    USED_PRIZE_CODE("017", "Khách hàng đã được cập phần thưởng trước đó"), /*The customer has already received the reward before*/
    USED_TICKET_ERROR("018", "Khách hàng không hợp lệ, vé đã được sử dụng cho khách hàng khác") /*Invalid customer, ticket already used*/
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
