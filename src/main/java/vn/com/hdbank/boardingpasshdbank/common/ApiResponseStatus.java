package vn.com.hdbank.boardingpasshdbank.common;

public enum ApiResponseStatus {
    SUCCESS("000", "Thành công"), /* Successfully */
    INTERNAL_SERVER_ERROR("001", "Lỗi máy chủ nội bộ"), /*Internal Server Error*/
    FORBIDDEN_ACCESS("002", "Quyền truy cập bị từ chối"), /*Forbidden*/
    UNAUTHORIZED_ACCESS("003", "Xác thực không hợp lệ"), /*Unauthorized access*/
    INVALID_CLIENT_REQUEST("004","Yêu cầu ứng dụng khách không hợp lệ"), /*Invalid client request*/
    DATABASE_ERROR("005", "Lỗi cơ sở dữ liệu"), /*Database error*/
    VALIDATE_INVALID_INPUT("006", "Đầu vào không hợp lệ"), /*Invalid input*/
    EXTERNAL_API_ERROR("007", "Lỗi khi gọi API bên ngoài"),  /*Error calling external API*/
    VIET_JET_API_ERROR("008", "Không gọi được Vietjet API"), /*Unable to call DatabaseValidation API*/
    RESPONSE_API_ERROR("009", "Dữ liệu từ Api Vietjet trả về không đúng"), /*The data from Vietjet Api returned is incorrect*/
    INVALID_TICKET("010", "Thông tin vé không hợp lệ"), /*Invalid ticket information */
    NOT_FOUND_CUSTOMER("011", "Không tìm thấy khách hàng trong hệ thống"), /*No customers found*/
    CUSTOMER_NOT_VIET_JET("012", "Khách hàng không phải là hành khách của Vietjet"),  /* Customer is not a DatabaseValidation passenger */
    NOT_ENOUGH_PRIZE("013", "Khách hàng không đủ điều kiện tham gia quay thưởng"), /*Customers are not eligible to participate in the drawing*/
    TICKET_EXIST("014", "Vé đã tồn tại và được gắn với 1 khách hàng"), /* Ticket already exists and is tied to 1 customer*/
    PROGRAM_ENDED("015", "Chương trình quay thưởng đã dừng"), /*The prize draw has stopped*/
    USED_TICKET_ERROR("016", "Khách hàng đã sử dụng vé khác và có mã số quay thưởng"), /*Customers who have used other tickets and issued a lucky draw code*/
    INVALID_CUSTOMER("017", "Thông tin khách hàng không chính xác với thông tin vé"), /*Customer information is incorrect with ticket information*/
    NO_PRIZE_CODE("018", "Khách hàng chưa có mã dự thưởng"), /*Customers do not have a coupon code*/
    CUSTOMER_PRIZE_SUCCESS("019", "Khách hàng đã quay số dự thưởng và nhận hoàn tiền thành công"), /*Customer has successfully dialed the lucky number and received the refund*/
    PRIZE_SUCCESS_NOT_DIALED("020", "Khách hàng đã được cấp số dự thưởng nhưng chưa quay số"), /*Customers have been assigned a prize number but have not participated in the lottery yet*/
    PRIZE_CODE_ERROR("021", "Mã dự thưởng của khách hàng không chính xác"), /*Customer's prize code is incorrect*/
    USED_PRIZE("022", "Khách hàng đã được cập phần thưởng trước đó"), /*The customer has already received the reward before*/
    UPDATE_PRIZE_ERROR ("023","Cập nhật thông tin dự thưởng không thành công")  /*Update ticket information failed */

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
