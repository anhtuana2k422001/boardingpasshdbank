package vn.com.hdbank.boardingpasshdbank.model.vietjet.request;


import jakarta.validation.constraints.*;
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
    @NotNull (message = Validate.MESSAGE_NOT_NULL)
    @Pattern(regexp = Validate.REGEXP_FLIGHT_CODE, message = Validate.MESSAGE_FLIGHT_CODE)
    private String flightCode;

    @NotNull (message = Validate.MESSAGE_NOT_NULL)
    @Pattern(regexp = Validate.REGEXP_RESERVATION_CODE, message = Validate.MESSAGE_RESERVATION_CODE)
    private String reservationCode;

    @NotNull (message = Validate.MESSAGE_NOT_NULL)
    @Pattern(regexp = Validate.REGEXP_SEATS, message = Validate.MESSAGE_SEATS)
    private String seats;

    @NotNull (message = Validate.MESSAGE_NOT_NULL)
    @Min(value = 1, message = Validate.MESSAGE_REQUIRED_ID)
    private Integer customerId;

    @NotNull (message = Validate.MESSAGE_NOT_NULL)
    private Boolean isCustomerVietJet;
}