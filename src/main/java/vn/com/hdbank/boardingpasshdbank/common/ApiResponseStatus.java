package vn.com.hdbank.boardingpasshdbank.common;

public enum ApiResponseStatus {
    SUCCESS("000", "Thành công"), /* Successfully */
    INTERNAL_SERVER_ERROR("001", "Lỗi máy chủ nội bộ"), /*Internal Server Error*/
    VIET_JET_EXISTED_AND_ASSIGNED("002", "Vé đã tồn tại và được gắn với 1 khách hàng"), /* Ticket already exists and is tied to 1 customer*/
    VIET_JET_API_ERROR("003", "Không gọi được Vietjet API"), /*Unable to call DatabaseValidation API*/
    EXTERNAL_API_ERROR("004", "Lỗi khi gọi API bên ngoài"),  /*Error calling external API*/
    VALIDATE_INVALID_INPUT("005", "Đầu vào không hợp lệ"), /*Invalid input*/
    INVALID_TICKET("006", "Thông tin vé không hợp lệ"), /*Invalid ticket information */
    CUSTOMER_NOT_VIET_JET("007", "Khách hàng không phải là hành khách của Vietjet"),  /* Customer is not a DatabaseValidation passenger */
    NOT_ENOUGH_CONDITION_FOR_PRIZE("008", "Khách hàng không đủ điều kiện tham gia quay thưởng"), /*Customers are not eligible to participate in the drawing*/
    NOT_FOUND_CUSTOMER("009", "Không tìm thấy khách hàng"), /*No customers found*/
    PROGRAM_ENDED("010", "Chương trình quay thưởng đã dừng"), /*The prize draw has stopped*/
    NO_PRIZE_CODE("011", "Khách hàng chưa có mã dự thưởng"), /*Customers do not have a coupon code*/
    CUSTOMER_PRIZE_SUCCESS("012", "Khách hàng đã quay số dự thưởng và nhận hoàn tiền thành công"), /*Customer has successfully dialed the lucky number and received the refund*/
    PRIZE_SUCCESS_NOT_DIALED("013", "Khách hàng đã được cấp số dự thưởng nhưng chưa tham gia quay số"), /*Customers have been assigned a prize number but have not participated in the lottery yet*/
    UPDATE_PRIZE_ERROR ("014","Cập nhật thông tin vé không thành công"),  /*Update ticket information failed */
    INVALID_CLIENT_REQUEST("015","Yêu cầu ứng dụng khách không hợp lệ"), /*Invalid client request*/
    DATABASE_ERROR("016", "Lỗi cơ sở dữ liệu"), /*Database error*/
    PRIZE_CODE_ERROR("017", "Mã dự thưởng của khách hàng không chính xác"), /*Customer's prize code is incorrect*/
    USED_PRIZE_CODE("018", "Khách hàng đã được cập phần thưởng trước đó"), /*The customer has already received the reward before*/
    USED_TICKET_ERROR("019", "Khách hàng không hợp lệ, vé đã được sử dụng cho khách hàng khác"), /*Invalid customer, ticket already used*/
    RESPONSE_API_ERROR("020", "Dữ liệu từ Api trả về không đúng"), /*Data from Api returned incorrect*/

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
