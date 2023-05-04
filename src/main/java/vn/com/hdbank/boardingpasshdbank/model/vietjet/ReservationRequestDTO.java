package vn.com.hdbank.boardingpasshdbank.model.vietjet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequestDTO {
    private String fullName;
    private String flightCode;
    private String reservationCode;
    private String seats;


    //add more getter setter
    private String getAirlineCode() {
        String airlineCode = StringUtils.substring(flightCode, 0, 2);
        return airlineCode;
    }

    private String getFlightNumber() {

        String flightNumber = StringUtils.substring(flightCode, 2);
        return flightNumber;
    }

    private String getSeatRow() {
        String seatRow = StringUtils.substring(seats, 0, StringUtils.length(seats) - 1);
        return seatRow;
    }
    private String getSeatCols() {
        String seatCols = StringUtils.substring(seats, StringUtils.length(seats) - 1);
        return seatCols;
    }


}
