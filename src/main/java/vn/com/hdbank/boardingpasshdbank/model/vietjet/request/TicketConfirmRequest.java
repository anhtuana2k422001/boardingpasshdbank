package vn.com.hdbank.boardingpasshdbank.model.vietjet.request;


import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketConfirmRequest {
    private String lastName;
    private String firstName;
    @Pattern(regexp = "^[A-Za-z]{2}\\d{1,4}$", message = "Flight code must start with 2 letters and be followed by 1 to 4 digits")
    private String flightCode;
    @Pattern(regexp = "^[a-zA-Z0-9]{1,6}$", message = "Reservation code must contain up to 6 alphanumeric characters")
    private String reservationCode;
    @Pattern(regexp = "^\\d{1,3}[a-zA-Z]$", message = "Seats must have 1 or 3 digits followed by a letter")
    private String seats;

    private Boolean isCustomerVietjet;

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