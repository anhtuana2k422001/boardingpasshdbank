package vn.com.hdbank.boardingpasshdbank.model.vietjet.request;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.hdbank.boardingpasshdbank.common.Validate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketConfirmRequest extends BaseRequest {
    private String lastName;
    private String firstName;
    @Pattern(regexp = Validate.FLIGHT_CODE., message = Validate.FLIGHT_CODE.)
    private String flightCode;
    @Pattern(regexp = "^[a-zA-Z0-9]{1,6}$", message = "Mã đặt chỗ phải chứa tối đa 6 ký tự chữ và số") /*Reservation code must contain up to 6 alphanumeric characters*/
    private String reservationCode;
    @Pattern(regexp = "^\\d{1,3}[a-zA-Z]$", message = "Ghế phải có 1 hoặc 3 chữ số theo sau là một chữ cái") /*Seats must have 1 or 3 digits followed by a letter*/
    @NotNull
    @NotEmpty (message = "")
    private String seats;
    @NotNull
    private Integer customerId;
    @NotNull
    private Boolean isCustomerVietJet;
}