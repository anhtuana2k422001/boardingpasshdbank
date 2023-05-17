package vn.com.hdbank.boardingpasshdbank.model.vietjet.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketScanRequest extends BaseRequest{
    @NotNull
    @NotEmpty
    private String formatCode;
    @NotNull
    @NotEmpty
    private String fullName;
    @NotNull
    @NotEmpty
    private String reservationCode;
    @NotNull
    @NotEmpty
    private String flightName; /* SG -> HN */
    @NotNull
    @NotEmpty
    private String airlineCode; /*  VJ */
    @NotNull
    @NotEmpty
    private String flightNumber; /*  0130 */
    @NotNull
    @NotEmpty
    private String boardingTime; /*  201  quy đổi từ 20/07 sang Julian date */
    @NotNull
    @NotEmpty
    private String checkInNumber; /* 003B0127: số ghế là 3B và số thứ tự check in là 127 */

    public String getFistName(){
        String firstName = StringUtils.substringAfter(fullName, "/");
        return StringUtils.trim(firstName);
    }

    public String getLastName(){
        String lastName = StringUtils.substringBefore(fullName, "/");
        return StringUtils.trim(lastName);
    }

    public String getFlightNumber() {
        return StringUtils.stripStart(flightNumber, "0");
    }

    public String getSeatRow() {
        return StringUtils.stripStart(StringUtils.substring(checkInNumber, 0, 3), "0");
    }

    public String getSeatCols() {
        return StringUtils.substring(checkInNumber, 3,  4);
    }

    public String getFlightCode(){
        return StringUtils.join(airlineCode, getFlightNumber());
    }

    public String getSeats(){
        return StringUtils.join(getSeatRow(), getSeatCols());
    }
}
