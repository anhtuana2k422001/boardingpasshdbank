package vn.com.hdbank.boardingpasshdbank.model.vietjet.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import vn.com.hdbank.boardingpasshdbank.common.Validate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequest extends BaseRequest {
    private String lastName;
    private String firstName;

    @NotNull (message = Validate.MESSAGE_NOT_NULL)
    @Pattern(regexp = Validate.REGEXP_FLIGHT_CODE, message = Validate.MESSAGE_FLIGHT_CODE)
    private String flightCode;

    @NotNull (message = Validate.MESSAGE_NOT_NULL)
    @Pattern(regexp = Validate.REGEXP_RESERVATION_CODE, message = Validate.MESSAGE_RESERVATION_CODE)
    private String reservationCode;

    @NotNull (message = Validate.MESSAGE_NOT_NULL)
    @Pattern(regexp = Validate.REGEXP_SEATS, message = Validate.MESSAGE_SEATS)
    private String seats;

    //add more getter setter
    public String getAirlineCode() {
        return StringUtils.substring(flightCode, 0, 2);
    }

    public String getFlightNumber() {
        return StringUtils.substring(flightCode, 2);
    }

    public String getSeatRow() {
        return StringUtils.substring(seats, 0, StringUtils.length(seats) - 1);
    }

    public String getSeatCols() {
        return StringUtils.substring(seats, StringUtils.length(seats) - 1);
    }

}