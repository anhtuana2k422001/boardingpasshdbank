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
    public String getAirlineCode() {
        return StringUtils.substring(flightCode, 0, 2);
    }

    public String getFlightNumber() {
<<<<<<< HEAD
        return  StringUtils.substring(flightCode, 2);
=======
        return StringUtils.substring(flightCode, 2);
>>>>>>> refs/remotes/origin/main
    }

    public String getSeatRow() {
        return StringUtils.substring(seats, 0, StringUtils.length(seats) - 1);
    }
<<<<<<< HEAD
=======

>>>>>>> refs/remotes/origin/main
    public String getSeatCols() {
        return StringUtils.substring(seats, StringUtils.length(seats) - 1);
    }


}
