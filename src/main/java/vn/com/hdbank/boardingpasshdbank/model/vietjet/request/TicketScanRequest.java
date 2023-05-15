package vn.com.hdbank.boardingpasshdbank.model.vietjet.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketScanRequest extends BaseRequest{
    private String formatCode;
    private String fullName;
    private String reservationCode;
    private String flightName; /* SG -> HN */
    private String airlineCode; /*  VJ */
    private String flightNumber; /*  0130 */
    private String boardingTime; /*  201  quy đổi từ 20/07 sang Julian date */
    private String checkInNumber; /* 03B0127: số ghế là 3B và số thứ tự check in là 127 */
}
