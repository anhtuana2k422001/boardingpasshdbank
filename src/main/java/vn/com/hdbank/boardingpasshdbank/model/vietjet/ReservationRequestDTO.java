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
<<<<<<< HEAD
        return  StringUtils.substring(flightCode, 2);
=======
        return StringUtils.substring(flightCode, 2);
>>>>>>> refs/remotes/origin/main
=======
        return StringUtils.substring(flightCode, 2);
>>>>>>> 9ed5d6d5149dacac5de8270e105f83f17bd7ddd2
    }

    public String getSeatRow() {
        return StringUtils.substring(seats, 0, StringUtils.length(seats) - 1);
    }
<<<<<<< HEAD
<<<<<<< HEAD
=======

>>>>>>> refs/remotes/origin/main
=======

>>>>>>> 9ed5d6d5149dacac5de8270e105f83f17bd7ddd2
    public String getSeatCols() {
        return StringUtils.substring(seats, StringUtils.length(seats) - 1);
    }


}
